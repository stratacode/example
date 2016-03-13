import java.io.IOException;

@PathServlet(path="/hello")
class HelloWorld extends HttpServlet {
   void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      response.getOutputStream().print("<body>Hello world</body>");
   }
}
