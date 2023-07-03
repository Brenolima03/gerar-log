import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class BaixarLogsPorData {
    public static void main(String[] args) {
        String extension = ".log.gz";
        // Essas datas dependem do formato que o log usa
        int initialX = 20230101;
        int finalX = 20230101;
        int initialY = 0;
        int finalY = 23;

        System.out.println("Iniciando o download, descompactação e exclusão dos logs...");
        for (int x = initialX; x <= finalX; x++) {
            for (int y = initialY; y <= finalY; y++) {
                String url2 = "http:/192.168.0.0/" + x + "/";
                String fileName = "log-" + x + "-" + String.format("%02d", y) + extension;
                String fileUrl = url2 + "caminho_url_com__nome_do_arquivo-" + x + "-" + String.format("%02d", y) + extension;
                String extractedFileName = fileName.replace(".gz", "");

                try (InputStream inputStream = new URL(fileUrl).openStream();
                     FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }

                    System.out.println("Log baixado: " + fileName);

                    // Descompactar o arquivo .gz
                    try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(fileName));
                         FileOutputStream extractedFileOutputStream = new FileOutputStream(extractedFileName)) {

                        byte[] extractBuffer = new byte[4096];
                        int extractBytesRead;
                        while ((extractBytesRead = gzipInputStream.read(extractBuffer)) != -1) {
                            extractedFileOutputStream.write(extractBuffer, 0, extractBytesRead);
                        }

                        System.out.println("Log descompactado: " + extractedFileName);
                        // Excluir o arquivo compactado
                        File compactedFile = new File(fileName);
                        compactedFile.deleteOnExit();
                    }
                    

                } catch (IOException e) {
                    System.out.println("Erro ao baixar, descompactar ou excluir o log " + fileName + ": " + e.getMessage());
                    continue; // Ir para o próximo log em caso de erro
                }
            }
        }

        System.out.println("Download, descompactação e exclusão dos logs concluídos!");
    }
}
