package servlet;

import com.google.gson.Gson;
import dao.PostDaoStorage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("/posts")
public class PostServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final PostDaoStorage postDaoStorage = new PostDaoStorage();
    private final Logger logger = Logger.getLogger(PostServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getQueryString() != null) {
            logger.info("GET-запрос на получение поста по id");
            Integer id = Integer.parseInt(request.getQueryString()) ;
            try {
                String postJsonString = gson.toJson(postDaoStorage.getPostById(id));
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(postJsonString);
                out.flush();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("GET-запрос на получение списка всех постов");
            try {
                String postsJsonString = gson.toJson(postDaoStorage.getAllPosts());
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(postsJsonString);
                out.flush();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
