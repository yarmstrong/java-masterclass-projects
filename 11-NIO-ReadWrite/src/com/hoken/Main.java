package com.hoken;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/*
  FileInputStream
  FileChannel => the source gotten from FileInputStream
*/
public class Main {
    public static void main(String[] args) {
        // nioReadWriteToStringFile();
        // nioReadWriteToByteFileMultipleBuffers(); // many logging msgs to aid in understanding
        // nioReadWriteToByteFileCollectThenReadWrite(); // the simplified version of above method
        // seekableByteChannel();
        // copyFileChannelToChannel("data2.dat","data2_copy.dat");
        readWriteUsingPipesInThreads();
    }

    private static void nioReadWriteToStringFile() {
        try {
            // uses bytes for write while String for read

            /* one file for readWrite */
            Path dataPath = FileSystems.getDefault().getPath("data.txt");

            /* WRITING sequentially to a file from String to bytes:
               Files.write() is an isolated write, it opens then closes at
               each call. So use StringBuilder to do chunks (ie 100 lines)
               then convert to bytes bec writing requires it to be in bytes */
            Files.write(dataPath, "\nLine 6".getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);

            /* READING sequentially all data in one go :
               reads entire data into memory. for such cases,
               its much better to use the java.io.* class */
            List<String> lines = Files.readAllLines(dataPath);

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void seekableByteChannel() {
        /* Same as RandomAccessFile, we now use one implementing SeekableByteChannel
           that requires the ff methods to be implemented:

           read(ByteBuffer) : reads bytes beginning of Channel's current bPos, and then
              updates the Channel's bPos with the last read position. note that we are
              updating the Channel, not the ByteBuffer position
           write(ByteBuffer) : same logic as reads, after write, position is updated to
              last write position. before writing, needs to verify if dataSource is
              opened in New/Truncate vs Append mode. Append has its starting position
              to be at end of dataSource, not 0.
           position() : returns Channel's position
           position(long) : sets Channels new position (overloaded method)
           truncate(long) : truncates size of attached dataSource to the passed value
           size() : returns size of attached dataSource

           luckily, FileChannel is already implementing SeekableByteChannel so we only
           need to use them right away
        */

        // saved positions from write to be accessed in read
        byte[] str1 = null;
        byte[] str2 = null;
        long int1Pos = 0;
        long int2Pos = 0;
        long int3Pos = 0;
        long str2Pos = 0;

        System.out.println("\n==== WRITING A SeekableByteChannel ====\n");

        // step1 : initialize the channel from a file stream
        try(FileOutputStream binFile = new FileOutputStream("data2.dat");
            FileChannel binFileChannel = binFile.getChannel()) {

            // step2 : initialize ByteBuffer with some size
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);

            // step3 : put data into ByteBuffer
            str1 = "Hello World!".getBytes();
            System.out.println("byteBuffer.position() before str1: " + byteBuffer.position() + '\n');
            byteBuffer.put(str1);

            int1Pos = str1.length;
            System.out.println("byteBuffer.position() before int1: " + byteBuffer.position()
                    + "; calculated int1Pos: " + int1Pos + '\n');
            byteBuffer.putInt(245);

            int2Pos = int1Pos + Integer.BYTES;
            System.out.println("byteBuffer.position() before int2: " + byteBuffer.position()
                    + "; calculated int2Pos: " + int2Pos + '\n');
            byteBuffer.putInt(-98765);

            str2Pos = int2Pos + Integer.BYTES;
            System.out.println("byteBuffer.position() before str2: " + byteBuffer.position()
                    + "; calculated str2Pos: " + str2Pos + '\n');
            str2 = "Nice to meet you".getBytes();
            byteBuffer.put(str2);

            int3Pos = str2Pos + str2.length;
            System.out.println("byteBuffer.position() before int3: " + byteBuffer.position()
                    + "; calculated int3Pos: " + int3Pos + '\n');
            byteBuffer.putInt(1000);
            System.out.println("byteBuffer.position after last int3: " + byteBuffer.position() + '\n');

            int numBytesWritten = binFileChannel.write(byteBuffer.flip());
            System.out.println("numBytesWritten @flip: " + numBytesWritten);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n==== READING A SeekableByteChannel ====\n");

        // step1 : initialize the channel this time from a RandomAccessFile
        try (RandomAccessFile ra = new RandomAccessFile("data2.dat", "rwd");
             FileChannel raChannel = ra.getChannel()) {

            /* step2 : initialize ByteBuffer for size (so that channel can do its piping).
               note however that we are doing random access and not reading all data in
               one go, so we allocate only what we need */
            ByteBuffer intByteBuffer = ByteBuffer.allocate(Integer.BYTES);
            System.out.println("intByteBuffer.position after allocate: " + intByteBuffer.position() + '\n');

            // step3 : channel to pipe the stream into intByteBuffer (specific data only)
            int numByteRead = raChannel.position(int3Pos).read(intByteBuffer);
            /* NOTE: we are reading data from a Channel (gotten from our dataSource), so before reading,
               we position it to the index where we want to get data from (thats why random access). */
            System.out.println("numByteRead for int3Pos: " + numByteRead);
            System.out.println("intByteBuffer.position after int3 read: " + intByteBuffer.position() + '\n');

            // print the data we have read (we refer to intByteBuffer, not the Channel coz thats the source)
            System.out.println("reading int3: " + intByteBuffer.flip().getInt());
            System.out.println("intByteBuffer.position after int3 print: " + intByteBuffer.position() + '\n');


            // now repeat the process for int 2 and 1 read from Channel and then print the ByteBuffer
            numByteRead = raChannel.position(int1Pos).read(intByteBuffer.flip());
            System.out.println("numByteRead for int1Pos: " + numByteRead);
            System.out.println("intByteBuffer.position after int1 read: " + intByteBuffer.position());
            System.out.println("reading int1: " + intByteBuffer.flip().getInt());
            System.out.println("intByteBuffer.position after int1 print: " + intByteBuffer.position() + '\n');


            numByteRead = raChannel.position(int2Pos).read(intByteBuffer.flip());
            System.out.println("numByteRead for int2Pos: " + numByteRead);
            System.out.println("intByteBuffer.position after int2 read: " + intByteBuffer.position());
            System.out.println("reading int2: " + intByteBuffer.flip().getInt());
            System.out.println("intByteBuffer.position after int2 print: " + intByteBuffer.position() + '\n');

            if (str2 != null) {
                ByteBuffer str2ByteBuffer = ByteBuffer.allocate(str2.length); // str2 is already a byte[]
                numByteRead = raChannel.position(str2Pos).read(str2ByteBuffer); /* sets the starting position
                    and then read() will fill up the allocation on the ByteBuffer u provided. note however
                    that it ur trying to include some int to ur original String only, then int will not be
                    properly converted back to int.*/
                System.out.println("numByteRead for str2: " + numByteRead);
                if (str2ByteBuffer.hasArray())
                    System.out.println("str2ByteBuffer after read: " + new String(str2ByteBuffer.array()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void nioReadWriteToByteFileCollectThenReadWrite() {

        System.out.println("\n==== WRITING ====\n");

        // step1 : initialize the channel from a file stream
        try(FileOutputStream binFile = new FileOutputStream("data2.dat");
            FileChannel binFileChannel = binFile.getChannel()) {

            // step2 : initialize ByteBuffer with some size
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);

            // step3 : put data into ByteBuffer
            byteBuffer.put("Hello World!".getBytes());
            byteBuffer.putInt(245);
            byteBuffer.putInt(-98765);
            byteBuffer.put("Nice to meet you".getBytes());
            byteBuffer.putInt(1000);
            System.out.println("byteBuffer.position after multiple put: " + byteBuffer.position() + '\n');

            // step4 : write ByteBuffer into the channel
            int numBytesWritten = 0;

            // numBytesWritten = binFileChannel.write(byteBuffer.position(20)); // copies from bPos@20 to 99 = 80bytes written (including remaining unoccupied)
            // System.out.println("numBytesWritten @20: " + numBytesWritten);
            // System.out.println("byteBuffer.position after write: " + byteBuffer.position() + '\n');
            //
            // numBytesWritten = binFileChannel.write(byteBuffer.flip()); // copies from bPos@0 to up to bPos@40 = 40bytes written (only what was really put)
            // System.out.println("numBytesWritten @flip: " + numBytesWritten);
            // System.out.println("byteBuffer.position after write: " + byteBuffer.position() + '\n');

            numBytesWritten = binFileChannel.write(byteBuffer.position(40).flip()); // copies from bPos@0 to up to bPos@40 = 40bytes written (only what was really put)
            System.out.println("numBytesWritten @40,@flip: " + numBytesWritten);
            System.out.println("byteBuffer.position after write: " + byteBuffer.position() + '\n');

            /* IMPORTANT : in a 100-byte ByteBuffer with 40-byte occupied,
               there is a remaining 60-byte. if u do Channel.write() with
               no flipping (bPos@40), then the data written wud be the
               remaining 60-byte (which shud not be).

               if position is manipulated, new bPos = starting point with
               eob (end of buffer) = ending point

               if position is not manipulated, @flip will make @0 = starting point
               and current/old bPos = ending point

               if position is manipulated then @flip, @flip makes @0 = starting
               point and new bPos = ending point */

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n==== READING ====\n");

        // step1 : initialize the channel this time from a RandomAccessFile, can also use:
        // FileInputStream fileIn = new FileInputStream("data.txt");
        // FileChannel channel = fileIn.getChannel();
        try (RandomAccessFile ra = new RandomAccessFile("data2.dat", "rwd");
             FileChannel raChannel = ra.getChannel()) {

            // step2 : initialize ByteBuffer for size (so that channel can do its piping)
            ByteBuffer readByteBuffer = ByteBuffer.allocate(100);
            System.out.println("readByteBuffer.position after allocate: " + readByteBuffer.position() + '\n');

            // step3 : channel to pipe the stream into readByteBuffer (all data read once)
            int numByteRead = raChannel.read(readByteBuffer);
            System.out.println("numByteRead: " + numByteRead);
            System.out.println("readByteBuffer.position after piping: " + readByteBuffer.position() + '\n');

            // step4 : read the data from the readByteBuffer
            readByteBuffer.flip();
            System.out.println("readByteBuffer.position after flip: " + readByteBuffer.position() + '\n');

            byte[] inputString = new byte[12];
            readByteBuffer.get(inputString);
            System.out.println("1st reading: " + new String(inputString));

            System.out.println("2nd reading: " + readByteBuffer.getInt());
            System.out.println("3rd reading: " + readByteBuffer.getInt());

            byte[] inputString2 = new byte["Nice to meet you".toCharArray().length];
            readByteBuffer.get(inputString2);
            System.out.println("4th reading: " + new String(inputString2));

            System.out.println("5th reading: " + readByteBuffer.getInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void nioReadWriteToByteFileMultipleBuffers() {
        try (FileOutputStream binFile = new FileOutputStream("data.dat");
             FileChannel binFileChannel = binFile.getChannel()) {

            /* uses FileInputStream and FileOutputStream */

            /* WRITING sequentially into binary file NIO methods:
               1. open an FileOutputStream
               2. get its channel
               3. setup buffer :
                  - buffer capacity => # of elements it can contain
                  - buffer position => index of the next element that
                    should be read or written (bPos <= bCap)
                  - buffer mark => to rewind to certain mark @reset or flip??
                  - with writing, u know the buffer capacity u need to
                    setup (with reading, ur not aware)
                  a. create a byte array from String
                  b. ByteBuffer.wrap() => wraps the byte array
                     - byteBuffer is now backed by the buffer (change in
                       one will be reflected to the other) => meaning
                       this is now a read-write buffer
                     - resets index to 0 and mark is undefined
               4. write the buffer into the channel and return how many
                  bytes were written
               5. close the channel

               NOTE: our ByteBuffer.wrap() is a string buffer and index
               reset to 0. for other types, its better to have a diff
               buffer for them (esp so that u cant use the primitive
               type's max capacity for the buffer allocation)

               Important to remember in creating ur new buffer, when u
               write into it, there is a new buffer position and its
               no longer @0. so when u write the buffer into the channel,
               u need to reset your buffer position back to @0 */


            /* transform ur String to byte[]. this is not the same as
               primitive type ie int that u need to use byte[] coz
               String is a collection of primitive char, so each char
               will be transformed into its own byte, and so for String,
               need a byte[] whereas single char dont need one */
            byte[] outputBytes = "Hello World!".getBytes();

            /* 1) initializing ByteBuffer for channel read-write using
               wrap method => byte[] and ByteBuffer refers to the same
               data so change to one will be reflected to the other +
               this flip@0 under the hood, tho succeeding read-write
               will then reflect the correct bPos, this is only at
               initialization */
            // ByteBuffer strBuffer = ByteBuffer.wrap(outputBytes);
            // System.out.println("strBufferWrap.position after wrap: " + strBuffer.position());
            // System.out.println();

            /* 2) initializing ByteBuffer for channel read-write using
               allocate method => the new ByteBuffer will be allocated
               with capacity of byte[] length and then byte[] data was
               copied into it, so now they are not connected to each
               other + unlike wrap method, bPos is not conveniently
               flip@0 for us, so we need to do that ourselves */
            ByteBuffer strBuffer = ByteBuffer.allocate(outputBytes.length);
            System.out.println("strBufferAllocate.position after allocate: " + strBuffer.position());
            strBuffer.put(outputBytes);
            System.out.println("strBufferAllocate.position after put: " + strBuffer.position());
            System.out.println();
            strBuffer.flip();


            // writing the string buffer into the channel
            int numBytes = binFileChannel.write(strBuffer);
            System.out.println("strBuffer Bytes written: " + numBytes + '\n');


            /* creating and writing intBuffer into the channel :
               initialize the intBuffer and then flip it @0 before writing */
            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
            System.out.println("intBuffer.limit: " + intBuffer.limit());
            intBuffer.putInt(245); // bPos = @4
            System.out.println("intBuffer.position: " + intBuffer.position());
            intBuffer.flip();
            System.out.println("intBuffer.position after flip: " + intBuffer.position());
            numBytes = binFileChannel.write(intBuffer);
            System.out.println("Bytes written: " + numBytes);
            System.out.println("intBuffer.position after writing: " + intBuffer.position() + '\n');


            /* reusing the intBuffer and then use it for writing again : since
               we've already used intBuffer for writing above, we need to flip it
               @0 first, and then we populate it, and flip it again before write */
            intBuffer.flip().putInt(-98765);
            numBytes = binFileChannel.write(intBuffer.flip());
            System.out.println("intBuffer re-use => flip@0, putInt, flip@0, channelWrite");
            System.out.println("Bytes written: " + numBytes + '\n');
            System.out.println("======= writing using ByteBuffer and Channels Done =======\n");



            /* READING sequentially from binary file using RandomAccessFile
               of java.io.* but not using java.nio.* channel so data
               would need to be read directly from RandomAccessFile and
               into a byte[] instead of byteBuffer

               NOTE: since the data is read into a byte[], its easy to
               convert it into a String */
            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
            byte[] b = new byte[outputBytes.length]; // the Hello World! container
            ra.read(b); // ra reads its data from 0 to the length of the byte[]
            System.out.println(new String(b));

            long int1 = ra.readInt();
            long int2 = ra.readInt();
            System.out.println(int1);
            System.out.println(int2);
            System.out.println("===== java.io reading done =====\n");



            /* READING sequentially from binary file using RandomAccessFile
               of java.io.* but now USING java.nio.* channel so data can
               now travel thru the channel created from RandomAccessFile
               and into a byteBuffer instead of byte[]

               NOTE: since the data is read into a ByteBuffer, and as
               explained in ByteBuffer.wrap(), ByteBuffer changes will
               mirror in the byte[] and so we just use the byte[] to
               convert it to String OR use strBuffer.array() to convert
               it first to a byte[]

               however: since we have used the strBuffer into writing before,
               we need to flip @0 first. remember that yeah this is a buffer
               but a buffer from a byte[], a fixed-length array. and so
               need to do flip before we read the data from the channel */
            ra = new RandomAccessFile("data.dat", "rwd");
            FileChannel raChannel = ra.getChannel();

            System.out.println("strBuffer.position: " + strBuffer.position());
            outputBytes[0] = 'a';
            outputBytes[1] = 'b';
            System.out.println("outputByte before reading data : " + new String(outputBytes));
            System.out.println("strBuffer.position after flip: " + strBuffer.flip().position());
            long numBytesRead = raChannel.read(strBuffer);
            System.out.println("outputByte after read: " + new String(outputBytes)); // if strBuffer is wrap-initialized, same value; else if allocate-initialized, only strBuffer is updated
            if (strBuffer.hasArray())
                System.out.println("strBuffer after read: " + new String(strBuffer.array()));
            System.out.println("strBuffer.position: " + strBuffer.position());
            System.out.println();

            /* reading 1st int, getInt() no flipping, but specifying the starting index
             (will not modify the bPos) */
            System.out.println("intBuffer.position before read: " + intBuffer.position());
            raChannel.read(intBuffer.flip());
            System.out.println("intBuffer.position after read: " + intBuffer.position());
            System.out.println("intBuffer flipped then printed: " + intBuffer.getInt(0)); // absolute read
            System.out.println();

            /* reading 2nd int into the same intBuffer, getInt() needs to be flip to use
               relative read (will modify the current bPos)*/
            System.out.println("intBuffer.position before read: " + intBuffer.position());
            raChannel.read(intBuffer.flip());
            System.out.println("intBuffer.position after read: " + intBuffer.position());
            System.out.println("intBuffer not flipped but index@0: " + intBuffer.flip().getInt()); // relative read
            System.out.println("===== java.nio reading done =====\n");

            raChannel.close();
            ra.close();


            System.out.println("\nPRIMITIVES BYTE COUNT");
            System.out.println("Boolean: " + "is 1 i think");
            System.out.println("Character.BYTES: " + Character.BYTES);
            System.out.println("Byte.BYTES: " + Byte.BYTES);
            System.out.println("Short.BYTES: " + Short.BYTES);
            System.out.println("Integer.BYTES: " + Integer.BYTES);
            System.out.println("Long.BYTES: " + Long.BYTES);
            System.out.println("Float.BYTES: " + Float.BYTES);
            System.out.println("Double.BYTES: " + Double.BYTES);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFileChannelToChannel(String file, String newFile) {
        /* we are reading from file and then writing into the newFile so
           file = FileInputStream and newFile = FileOutputStream. but to
           make our life easier, we can just use RandomAccessFile which
           allows both reading and writing */
        try (FileInputStream inFile = new FileInputStream(file);
        // try (FileOutputStream inFile = new FileOutputStream(file, true);
             FileChannel inFileChannel = inFile.getChannel()) {

            // File newFilename = new File(newFile);
            try(FileOutputStream outFile = new FileOutputStream(newFile);
                FileChannel outFileChannel = outFile.getChannel()) {

                // long numTransferred = outFileChannel.transferFrom(inFileChannel, 0, inFileChannel.size()); // writable channel TRANSFER data FROM readable one

                long numTransferred = inFileChannel.transferTo(0, inFileChannel.size(), outFileChannel); // readable channel TRANSFER data TO writable one

                System.out.println("numTransferred: " + numTransferred);
                System.out.println("File copied successfully.");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fail creating new file");
            } catch (NonReadableChannelException e) {
                Files.delete(Path.of(newFile));
                System.out.println("File copy failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail opening file.");
        }
    }

    private static void readWriteUsingPipesInThreads() {
        try {
            Pipe pipe = Pipe.open(); // throws IOException
            Runnable writer = () -> {
                try {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(56); // u put data here to sink later
                    Pipe.SinkChannel sinkChannel = pipe.sink(); // u sink the data here

                    /* populate the ByteBuffer, write/sink data into SinkChannel,
                       then override ByteBuffer with new data, and sink it into
                       SinkChannel, rinse repeat for 10 loops */
                    for (int i = 0; i < 10; i++) {
                        String currTime = "The time is: " + System.currentTimeMillis();
                        System.out.println("currTime.length(): " + currTime.length()); // length is only 26
                        byteBuffer.put(currTime.getBytes()).flip(); // remember ByteBuffer accepts byte[] for String

                        while (byteBuffer.hasRemaining()) {
                            sinkChannel.write(byteBuffer); // throws IOException
                        }
                        byteBuffer.flip();
                        Thread.sleep(100); // throws InterruptedException
                        /* => inactivating the thread actually guarantees that once writer has sink
                           data into it, the reader thread will start and so will be able to read
                           what is written. so it looks alternating write-read process */
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            };

            Runnable reader = () -> {
                try {
                    Pipe.SourceChannel sourceChannel = pipe.source(); // u get data from this source
                    ByteBuffer byteBuffer = ByteBuffer.allocate(56); // u put read data here

                    for (int i = 0; i < 10; i++) {
                        int numByteRead = sourceChannel.read(byteBuffer); // throws IOException

                        byte[] timeString = new byte[numByteRead]; // there is only 26 bytes written so u get only 26
                        byteBuffer.flip().get(timeString); // ByteBuffer(56)@0 returns all 56 and so timeString is made full
                        System.out.println("Reader Thread: " + new String(timeString));

                        byteBuffer.flip();
                        Thread.sleep(100); // throws InterruptedException
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            };

            /* 2 separate threads started, both using the same Pipe. one writes into it 10 times thru
               its Pipe.SinkChannel, while the other reads from its Pipe.sourceChannel 10 times */
            new Thread(writer).start();
            new Thread(reader).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
