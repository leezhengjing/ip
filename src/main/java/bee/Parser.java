package bee;

public class Parser {

    enum TaskClass {
        TODO, DEADLINE, EVENT
    }

    enum TaskAction {
        MARK, UNMARK, DELETE
    }

    public static boolean parseUserCommand(String userInput, TaskList tasks, Storage storage, Ui ui) {
        String[] splitInput = userInput.split(" ");
        String command = splitInput[0].toLowerCase();
        try {
            switch (command) {
                case "bye":
                    ui.farewell();
                    return false;
                case "list":
                    tasks.listAllTasks();
                    break;
                case "todo":
                    tasks.createTask(TaskClass.TODO, userInput);
                    break;
                case "deadline":
                    tasks.createTask(TaskClass.DEADLINE, userInput);
                    break;
                case "event":
                    tasks.createTask(TaskClass.EVENT, userInput);
                    break;
                case "mark":
                    tasks.updateTask(TaskAction.MARK, userInput);
                    break;
                case "unmark":
                    tasks.updateTask(TaskAction.UNMARK, userInput);
                    break;
                case "delete":
                    tasks.updateTask(TaskAction.DELETE, userInput);
                    break;
                default:
                    throw new BeeException("Sorry, you need to use a command!");

            }
        } catch (BeeException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println("    _  _\n" +
                    "   | )/ )\n" +
                    "\\\\ |//,' __\n" +
                    "(\")(_)-\"()))=-\n" +
                    "   (\\\\ BZZZZZZZ!!!! Something went very wrong!!");
        }
        storage.saveTasksToFile();
        return true;
    }
}
