import edu.aitu.oop3.db.entities.*;
import edu.aitu.oop3.db.database.*;
import edu.aitu.oop3.db.entities.orderType.OrderType;
import edu.aitu.oop3.db.exceptions.*;
import edu.aitu.oop3.db.repositories.*;
import edu.aitu.oop3.db.repositories.impl.*;
import edu.aitu.oop3.db.services.*;
import edu.aitu.oop3.db.factory.OrderTypeFactory;
import java.util.*;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Connecting to Supabase...");

        IDB db = new PostgresDB();
        MenuItemRepository menuRepo = new MenuItemRepositoryImpl(db);
        OrderRepository orderRepo = new OrderRepositoryImpl(db);

        MenuService menuService = new MenuService(menuRepo);
        PaymentService paymentService = new PaymentService();
        OrderService orderService = new OrderService(orderRepo, menuService, paymentService);

        Scanner sc = new Scanner(System.in);
        OrderType currentOrderType = OrderTypeFactory.create("DINEIN");

        while (true) {
            System.out.println("\n--- Canteen Ordering ---");
            System.out.println("1) List menu");
            System.out.println("2) Place order");
            System.out.println("3) View active orders");
            System.out.println("4) Mark order completed");
            System.out.println("5) Select order type");
            System.out.println("6) Calculate order total by ID");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            try {
                if (choice.equals("1")) {
                    List<MenuItem> menu = menuService.getAllMenu();
                    menu.forEach(System.out::println);

                } else if (choice.equals("2")) {
                    System.out.print("Customer ID: ");
                    long customerId = Long.parseLong(sc.nextLine().trim());

                    List<OrderItem> items = new ArrayList<>();
                    while (true) {
                        System.out.print("Enter menuItemId (or 'done'): ");
                        String s = sc.nextLine().trim();
                        if (s.equalsIgnoreCase("done")) break;

                        long menuItemId = Long.parseLong(s);
                        System.out.print("Quantity: ");
                        int qty = Integer.parseInt(sc.nextLine().trim());

                        items.add(new OrderItem(0, 0, menuItemId, qty, null));
                    }

                    long orderId = orderService.placeOrder(customerId, items, currentOrderType);
                    System.out.println("Order created as " + currentOrderType.getType() + "! ID = " + orderId);

                } else if (choice.equals("3")) {
                    List<Order> active = orderService.getActiveOrders();
                    active.forEach(System.out::println);

                } else if (choice.equals("4")) {
                    System.out.print("Order ID: ");
                    long orderId = Long.parseLong(sc.nextLine().trim());
                    orderService.markCompleted(orderId);
                    System.out.println("Order " + orderId + " marked as COMPLETED.");

                } else if (choice.equals("5")) {
                    System.out.println("=== NEW ORDER CONFIGURATION ===");
                    System.out.println("1. Delivery");
                    System.out.println("2. Dine-in");
                    System.out.println("3. Pickup");
                    System.out.print("Enter number (1-3): ");
                    String typeChoice = sc.nextLine().trim();
                    String typeString;
                    typeString = switch (typeChoice) {
                        case "1" -> "DELIVERY";
                        case "2" -> "DINEIN";
                        case "3" -> "PICKUP";
                        default -> throw new IllegalArgumentException("Invalid option selected: " + typeChoice);
                    };
                    currentOrderType = OrderTypeFactory.create(typeString);
                    System.out.println("Success! Type set to: " + currentOrderType.getType());

                } else if (choice.equals("6")) {
                    System.out.print("Enter order ID: ");
                    long orderId = Long.parseLong(sc.nextLine());

                    BigDecimal total = orderService.calculateOrderTotalWithTax(orderId);
                    System.out.println("Order total with tax = " + total);

                } else if (choice.equals("0")) {
                    System.out.println("Bye!");
                    return;
                } else {
                    System.out.println("Unknown option.");
                }

            } catch (InvalidQuantityException | MenuItemNotAvailableException | OrderNotFoundException e) {
                System.out.println("Business error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}