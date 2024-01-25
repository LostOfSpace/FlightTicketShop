package BusinessLogic;

import com.google.gson.Gson;
import org.mindrot.bcrypt.BCrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import Entities.User;
import Entities.User.UserType;

public class UserManager {

  private static final String USERS_FOLDER = "Users/";

  static {
    // Перевірка наявності папки та створення, якщо вона відсутня
    File folder = new File(USERS_FOLDER);
    if (!folder.exists()) {
      folder.mkdirs(); // Створити папку та всі необхідні підпапки
    }
  }

  public static void registerUser(String login, String password, UserType userType) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    User user = new User(login, hashedPassword, userType);
    // Зберегти користувача в JSON файл
    saveUserToJsonFile(user);
  }

  public static User loginUser(String login, String password) {
    // Зчитати користувача з JSON файла
    User user = getUserFromJsonFile(login);

    // Перевірити пароль
    if (user != null && BCrypt.checkpw(password, user.getPassword())) {
      return user;
    }

    return null; // Невірний логін або пароль
  }

  private static void saveUserToJsonFile(User user) {
    Gson gson = new Gson();
    String filePath = USERS_FOLDER + user.getLogin() + ".json";

    try (FileWriter writer = new FileWriter(filePath)) {
      gson.toJson(user, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static User getUserFromJsonFile(String login) {
    Gson gson = new Gson();
    String filePath = USERS_FOLDER + login + ".json";

    try (FileReader reader = new FileReader(filePath)) {
      return gson.fromJson(reader, User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
