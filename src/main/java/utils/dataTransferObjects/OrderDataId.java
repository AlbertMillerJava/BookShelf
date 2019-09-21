package utils.dataTransferObjects;

import com.example.type.Customer;
import com.example.type.Order;
import com.example.type.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDataId {
    private long orderId;
    private List<ItemData> orderItems;

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setItemData(List<ItemData> orderItems) {
        this.orderItems = orderItems;
    }

    public Order createOrderById(){
        Order order  =  new Order();

        order.setOrderId(orderId);

        List<OrderItem> orderedItems = new ArrayList<>();
        for(ItemData itemData: orderItems){
            orderedItems.add(itemData.createOrderItem());
        }
        order.setOrderedItems(orderedItems);
        return order;
    }
}
