import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookShop     chitaiGorod = new BookShop();
        BookShop.Cli cli         = chitaiGorod.new Cli();

        cli.start(true);
    }
}

public class BookShop {
    private int                 balance = 0;
    private ArrayList<Object[]> books   = new ArrayList<Object[]>();

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBooks(ArrayList<Object[]> books) {
        this.books = books;
    }

    public int getBalance() {
        return this.balance;
    }

    public Object getBooks() {
        return this.books;
    }

    public int findBook(String name) {
        for (int i=0; i<this.books.size(); i++) {
            if (books.get(i)[0].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public Object[] buyBook(String name, int count) {
        int bookIndex = this.findBook(name);
        if (bookIndex!=-1) {
            int cost      = (int) this.books.get(bookIndex)[2];
            int bookCount = (int) this.books.get(bookIndex)[1];

            if (this.balance>=cost * count && count<=bookCount) {
                this.balance -= cost * count;
                this.books.get(bookIndex)[1] = bookCount - count;
                this.books.get(bookIndex)[3] = (int) this.books.get(bookIndex)[3] + count;

                return new Object[]{true,bookIndex};
            }
        }
        return new Object[]{false,bookIndex};
    }

    public Boolean editBooks(Object[] book) {
        if (book.length>=4) {
            int bookIndex = this.findBook((String) book[0]);
            if (bookIndex!=-1) {
                this.books.set(bookIndex, book);
            } else {
                this.books.add(book);
            }
            return true;
        }
        return false;
    }

    public class Cli {
        public void start(Boolean isGetingString) {
            String  command;
            Scanner input = new Scanner(System.in);

            if (isGetingString) {
                System.out.print("Enter data string: ");
                this.setDataFromString(input.nextLine());
            }

            mainCliLoop: while (true) {
                System.out.print(">");
                command = input.nextLine();
                
                try {
                    //coomands with input
                    if (command.length()>4 && command.substring(0,4).equals("buy ")) {
                        String name  = command.split("\"")[1];
                        int    count = Integer.parseInt(command.split("\"")[2].replaceAll(" ", ""));
                        
                        Object[] purchase = BookShop.this.buyBook(name, count);
                        if ((Boolean) purchase[0]) {
                            System.out.println("deal");
                        } else {
                            System.out.println("no deal");
                        }
                    } else if (command.length()>11 && command.substring(0,11).equals("edit books ")) {
                        Boolean isEdited = BookShop.this.editBooks(new Object[]{
                            command.split("\"")[1],                                 //book name
                            Integer.parseInt(command.split("\"")[2].split(" ")[1]), //books count
                            Integer.parseInt(command.split("\"")[2].split(" ")[2]), //book cost
                            Integer.parseInt(command.split("\"")[2].split(" ")[3])  //sold count
                        });

                        if (isEdited) {
                            System.out.println("Book was edited");
                        } else {
                            System.out.println("Error occured");
                        }
                    } else if (command.length()>12 && command.substring(0,12).equals("set balance ")) {
                        BookShop.this.setBalance(Integer.parseInt(command.split("balance ")[1]));
                        System.out.println("Balance edited");
                    } else {
                    //commands without input
                        switch (command) {
                            case "print balance":
                                System.out.println(BookShop.this.getBalance());
                                break;
                            case "show books in stock":
                                this.printBooks(true);
                                break;
                            case "show bought books":
                                this.printBooks(false);
                                break;
                            case "exit":
                                break mainCliLoop;
                            case "":
                                break;
                            default:
                                System.out.println("I don't understand");
                                break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("------------------[Error occured]-----------------\n");
                    System.out.println(e.getMessage());
                    System.out.println("\n---[Exception was processed. Program continues]---");
                }
            }
            input.close();
        }

        public void setDataFromString(String line) {
            //seting balance
            BookShop.this.setBalance(Integer.parseInt(line.split(",",2)[0].split("balance: ")[1]));
   
            for (String book : line.split("books: ")[1].split("\\),")) {
                BookShop.this.books.add(new Object[]{
                    book.split("\"")[1],                                                    //book name
                    Integer.parseInt(book.split("\", ")[1].split(", ")[0]),                 //books count
                    Integer.parseInt(book.split("\", ")[1].split(", ")[1].split("\\)")[0]), //book cost
                    0                                                                       //sold count
                });
            }
        }

        private void printBooks(Boolean isFromStock) {
            for (Object[] book : BookShop.this.books) {
                if ((int) book[1]!=0 && isFromStock) {
                    System.out.println(String.format("\"%s\", %d шт., %d руб.", book[0], book[1], book[2]));
                } else if ((int) book[3]!=0 && !isFromStock) {
                    System.out.println(String.format("\"%s\", %d шт.", book[0], book[3]));
                }
            }
        }

    }
}
