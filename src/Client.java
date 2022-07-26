import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 25001);
        final SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(socketAddress);
        Scanner scanner = new Scanner(System.in);

        final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
        String msg;
        while (true) {
            System.out.println("Enter some text for server: ");
            msg = scanner.nextLine();
            if (msg.equals("end")) break;

            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));

            int byteCount = socketChannel.read(inputBuffer);
            System.out.println(new String(inputBuffer.array(), 0, byteCount, StandardCharsets.UTF_8));
            inputBuffer.clear();

        }
        socketChannel.close();
    }
}
