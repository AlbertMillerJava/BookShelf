package utils.dataTransferObjects;

import com.example.type.Book;
import com.example.type.OrderItem;

public class ItemData {
    int bookId;
    int amount;

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public OrderItem createOrderItem(){
        Book book = new Book();
        book.setId(bookId);
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(amount);
        orderItem.setBook(book);
        return orderItem;
    }
}
