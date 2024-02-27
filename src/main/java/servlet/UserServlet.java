package servlet;

import com.google.gson.Gson;
import dao.UserDaoStorage;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserDaoStorage userDaoStorage = new UserDaoStorage();
    private final Logger logger = Logger.getLogger(UserServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getQueryString() != null) {
            logger.info("GET-запрос на получение пользователя по id");
            String id = request.getQueryString();
            try {
                String userJsonString = gson.toJson(userDaoStorage.getUserById(id));
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(userJsonString);
                out.flush();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("GET-запрос на получение списка всех пользователей");
            try {
                String usersJsonString = gson.toJson(userDaoStorage.getUsers());
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(usersJsonString);
                out.flush();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("POST-запрос на добавление пользователя");
        String jsonString = extractPostRequestBody(req);
        User user = gson.fromJson(jsonString, User.class);
        try {
            userDaoStorage.addUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("Добавлен новый пользователь с id = " + user.getId());
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(user));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("PUT-запрос на обновление пользователя");
        String jsonString = extractPostRequestBody(request);
        User user = gson.fromJson(jsonString, User.class);
        try {
            userDaoStorage.updateUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("Обновлен пользователь с id = " + user.getId());
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(user));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        logger.info("DELETE-запрос на удаление пользователя");
        if (request.getQueryString()!= null) {
            String id = request.getQueryString();
            try {
                userDaoStorage.deleteUserById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String extractPostRequestBody(HttpServletRequest request) throws IOException {
        String requestBody = null;
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            requestBody = sb.toString();
        }
        return requestBody;
    }
}
