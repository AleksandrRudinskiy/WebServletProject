package servlet;

import com.google.gson.Gson;
import dao.FollowersDaoStorage;
import model.UserFollowers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/followers/posts/user")
public class UserFollowersServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(UserFollowersServlet.class.getName());
    private final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
    private final FollowersDaoStorage followersDaoStorage = new FollowersDaoStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getQueryString() != null) {
            logger.info("GET-запрос на получение списков подписчиков и постов пользователя по id");
            String userId = request.getQueryString();
            UserFollowers userFollowers = followersDaoStorage.getFollowers(userId);
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(userFollowers));
            out.flush();
        }



    }
}
