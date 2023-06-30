import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BaixarLogsDoDia {
    public static void main(String[] args) {
        String baseUrl = "";
        String extension = ".log";
        int initialX = 0;
        int finalX = 0;
        int initialY = 0;
        int finalY = 24;

        System.out.println("Iniciando o download dos logs...");
        for (int x = initialX; x <= finalX; x++) {
            for (int y = initialY; y < finalY; y++) {
                String fileName = "log-" + x + "-" + String.format("%02d", y) + extension;

                try {
                    URL url = new URL(baseUrl + x + "-" + String.format("%02d", y) + extension);
                    InputStream inputStream = url.openStream();

                    FileOutputStream fileOutputStream = new FileOutputStream(fileName);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    fileOutputStream.close();
                    inputStream.close();

                    System.out.println("Log " + fileName + " criado");
                } 
                catch (IOException e) {
                    System.out.println("O " + fileName + " ainda não foi gerado");
                }
            }
        }
    }
}
