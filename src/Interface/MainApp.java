package Interface;

// MainApp.java
import BusinessLogic.UserManager;
import Entities.User;
import Entities.User.UserType;  // Виправлено імпорт
import java.util.Scanner;

public class MainApp {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("1. Реєстрація");
    System.out.println("2. Вхід");
    int choice = scanner.nextInt();

    switch (choice) {
      case 1:
        registerUser();
        break;
      case 2:
        loginUser();
        break;
      default:
        System.out.println("Невірний вибір");
    }
  }

  private static void registerUser() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Введіть логін:");
    String login = scanner.nextLine();

    System.out.println("Введіть пароль:");
    String password = scanner.nextLine();

    System.out.println("Виберіть тип аккаунту (1 - Admin, 2 - User):");
    int userTypeChoice = scanner.nextInt();
    UserType userType = (userTypeChoice == 1) ? UserType.ADMIN : UserType.USER;

    UserManager.registerUser(login, password, userType);
    System.out.println("Реєстрація завершена");
  }

  private static void loginUser() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Введіть логін:");
    String login = scanner.nextLine();

    System.out.println("Введіть пароль:");
    String password = scanner.nextLine();

    User user = UserManager.loginUser(login, password);

    if (user != null) {
      System.out.println("Вхід виконаний успішно. Тип аккаунту: " + user.getUserType());
    } else {
      System.out.println("Невірний логін або пароль");
    }
  }
}
