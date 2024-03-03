package servlet;

import com.google.gson.Gson;
import dao.UserDaoStorage;
import exception.NotFoundException;
import model.User;
import storage.UserStorage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserStorage userStorage = new UserDaoStorage();
    private final Logger logger = Logger.getLogger(UserServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getQueryString() != null) {
            logger.info("GET-запрос на получение пользователя по id");
            String id = request.getQueryString();
            try {
                User user = userStorage.getUserById(id);
                String userJsonString = gson.toJson(user);
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(userJsonString);
                out.flush();
            } catch (NotFoundException e) {
                response.setStatus(400);
                logger.info("Пользователь с id = " + id + "не найден.");
            }
        } else {
            logger.info("GET-запрос на получение списка всех пользователей");
            String usersJsonString = gson.toJson(userStorage.getUsers());
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(usersJsonString);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("POST-запрос на добавление пользователя");
        String jsonString = extractPostRequestBody(req);
        User user = gson.fromJson(jsonString, User.class);
        userStorage.addUser(user);
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
        userStorage.updateUser(user);
        logger.info("Обновлен пользователь с id = " + user.getId());
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(user));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        logger.info("DELETE-запрос на удаление пользователя");
        if (request.getQueryString() != null) {
            String id = request.getQueryString();
            try {
                userStorage.deleteUserById(id);
            } catch (NotFoundException e) {
                response.setStatus(400);
                logger.info("Пользователь с id = " + id + "не найден.");
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
