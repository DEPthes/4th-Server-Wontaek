package taek.servlet.servlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

    public static void main(String[] args) {
        final int PORT = 8080;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("직접 만든 HTTP 서버가 " + PORT + "번 포트에서 시작되었습니다.");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    // 요청 전체 읽기
                    StringBuilder rawRequest = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        rawRequest.append(line).append("\r\n");
                    }

                    // 직접 만든 파서 사용
                    MyHttpRequest request = new MyHttpRequest(rawRequest.toString());

                    System.out.println("요청 메소드: " + request.getMethod());
                    System.out.println("요청 경로: " + request.getPath());
                    System.out.println("요청 파라미터 name: " + request.getParameter("name"));

                    // 간단한 응답
                    String responseBody = "Hello, " + (request.getParameter("name") != null ? request.getParameter("name") : "World") + "!";
                    String httpResponse =
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: text/plain\r\n" +
                                    "Content-Length: " + responseBody.length() + "\r\n" +
                                    "\r\n" +
                                    responseBody;

                    out.write(httpResponse);
                    out.flush();
                } catch (IOException e) {
                    System.out.println("클라이언트 연결 오류: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("서버 시작 실패: " + e.getMessage());
        }
    }
}
