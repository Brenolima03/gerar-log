import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BaixarLogsPorData {
    public static void main(String[] args) {
        String extension = ".log.gz";
        // Essas datas dependem do formato que o log usa
        int initialX = 20230626;
        int finalX = 20230627;
        
        int initialY = 0;
        int finalY = 23;

        System.out.println("Iniciando o download dos logs...");
        for (int x = initialX; x <= finalX; x++) {
            for (int y = initialY; y <= finalY; y++) {
                String url = "http://192.168.0.0/" + x + "/";
                String fileName = "log-" + x + "-" + String.format("%02d", y) + extension;
                String fileUrl = url + "caminho_url_com__nome_do_arquivo" + x + "-" + String.format("%02d", y) + extension;

                try (InputStream inputStream = new URL(fileUrl).openStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    System.out.println("Log baixado: " + fileName);
                } catch (IOException e) {
                    System.out.println("Erro ao baixar o log " + fileName + ": " + e.getMessage());
                }
            }
        }
        System.out.println("Download dos logs concluído!");
    }
}
