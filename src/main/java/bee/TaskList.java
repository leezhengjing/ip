package bee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void quietlyAddTask(Task task) {
        this.tasks.add(task);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        System.out.println("Got it. I've added this task: ~Bzzz~");
        System.out.println("\t" + task.toString());
        System.out.println("Now you have " + this.tasks.size() + " tasks in the list. ~Bzzz~");
    }

    public void listAllTasks() {
        System.out.println("Here are the tasks in your list: ~Bzzz~");
        for (int i = 0; i < this.tasks.size() ; i++) {
            System.out.println((i + 1) + ". " + this.tasks.get(i).toString());
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTaskDone(int taskIndex) throws BeeException {
        if (taskIndex > this.tasks.size() || taskIndex < 1) {
            throw new BeeException("OOPS!! Please enter a valid task number.");
        }
        this.tasks.get(taskIndex - 1).setDone();
        System.out.println("Nice! I've marked task " + taskIndex +  " as done: ~Bzzz~");
        System.out.println(this.tasks.get(taskIndex - 1).toString());
    }

    public void setTaskNotDone(int taskIndex) throws BeeException {
        if (taskIndex > this.tasks.size() || taskIndex < 1) {
            throw new BeeException("OOPS!! Please enter a valid task number.");
        }
        this.tasks.get(taskIndex - 1).setNotDone();
        System.out.println("OK, I've marked task " + taskIndex +  " as not done yet: ~Bzzzz");
        System.out.println(this.tasks.get(taskIndex - 1).toString());
    }

    public void deleteTask(int taskIndex) throws BeeException {
        if (taskIndex > tasks.size() || taskIndex < 1) {
            throw new BeeException("OOPS!! Please enter a valid task number.");
        }
        Task deletedTask = this.tasks.get(taskIndex - 1);
        this.tasks.remove(taskIndex - 1);
        System.out.println("Okies~. I've removed this task:");
        System.out.println(deletedTask.toString());
        System.out.println("Now you have " + this.tasks.size() + " tasks in the list.");
    }

    public void createTask(Parser.TaskClass task, String userInput) throws BeeException {
        switch (task) {
            case TODO:
                try {
                    String editedInput = userInput.substring(5);
                    if (editedInput.isEmpty()) {
                        throw new BeeException("OOPS!! The description of a todo cannot be empty.");
                    }
                    Todo todo = new Todo(editedInput);
                    this.addTask(todo);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new BeeException("OOPS!! The description of a todo cannot be empty.");
                }
                break;
            case DEADLINE:
                try {
                    String editedInput = userInput.substring(9);
                    if (editedInput.isEmpty()) {
                        throw new BeeException("OOPS!! The description of a deadline cannot be empty.");
                    }
                    String[] splitEditedInput = editedInput.split(" /by ");
                    String deadlineDescription = splitEditedInput[0];
                    String deadlineDateString = splitEditedInput[1];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime deadlineDate = LocalDateTime.parse(deadlineDateString, formatter);

                    Deadline deadlineTask = new Deadline(deadlineDescription, deadlineDate);
                    this.addTask(deadlineTask);
                } catch (DateTimeParseException e) {
                    throw new BeeException("OOPS!! Invalid deadline date format. Please use yyyy-MM-dd HHmm");
                }
                catch (StringIndexOutOfBoundsException e) {
                    throw new BeeException("OOPS!! The description of a deadline cannot be empty.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new BeeException("OOPS!! The date of the deadline cannot be empty.");
                }
                break;
            case EVENT:
                try {
                    String editedInput = userInput.substring(6);
                    if (editedInput.isEmpty()) {
                        throw new BeeException("OOPS!! The description of an event cannot be empty.");
                    }
                    String[] splitEditedInput = editedInput.split(" /from ");
                    String[] splitEditedInput2 = splitEditedInput[1].split(" /to ");
                    String eventDescription = splitEditedInput[0];
                    String eventStartDate = splitEditedInput2[0];
                    String eventEndDate = splitEditedInput2[1];
                    Event event = new Event(eventDescription, eventStartDate, eventEndDate);
                    this.addTask(event);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new BeeException("OOPS!! The description of an event cannot be empty.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new BeeException("OOPS!! The date of an event cannot be empty.");
                }
                break;

            default:
                this.addTask(new Task(userInput));
        }
    }
    public void updateTask(Parser.TaskAction action, String userInput) throws BeeException {
        String[] splitInput = userInput.split(" ");
        switch (action) {
            case MARK:
                try {
                    int taskIndex = Integer.parseInt(splitInput[1]);
                    this.setTaskDone(taskIndex);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new BeeException("OOPS!! The task number cannot be empty.");
                } catch (NumberFormatException e) {
                    throw new BeeException("OOPS!! You must have entered an invalid task number.");
                }
                break;
            case UNMARK:
                try {
                    int taskIndex = Integer.parseInt(splitInput[1]);
                    this.setTaskNotDone(taskIndex);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new BeeException("OOPS!! The task number cannot be empty.");
                } catch (NumberFormatException e) {
                    throw new BeeException("OOPSS!! You must have entered an invalid task number.");
                }
                break;
            case DELETE:
                try {
                    int taskIndex = Integer.parseInt(splitInput[1]);
                    this.deleteTask(taskIndex);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new BeeException("OOPSS!! Please enter a task number");
                } catch (NumberFormatException e) {
                    throw new BeeException("OOPSS!! You must have entered an invalid task number.");
                }
                break;
            default:
                throw new BeeException("OOPSS!! I can't do that!!!");
        }
    }
}
