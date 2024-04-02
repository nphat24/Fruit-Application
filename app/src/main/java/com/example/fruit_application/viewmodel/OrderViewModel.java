package com.example.fruit_application.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.fruit_application.database.IRealmResponse;
import com.example.fruit_application.database.helper.CartDBHelper;
import com.example.fruit_application.database.helper.OrderDBHelper;
import com.example.fruit_application.database.modelRealm.CartRealmModel;
import com.example.fruit_application.database.modelRealm.OrderRealmModel;
import com.example.fruit_application.model.Cart;
import com.example.fruit_application.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {
    private OrderDBHelper orderDBHelper = OrderDBHelper.getInstance();
    private CartDBHelper cartDBHelper = CartDBHelper.getInstance();
    List<Order> list = new ArrayList<>();
    private  Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void saveOrder(Cart cart, String delivery, Boolean isShipped){
        orderDBHelper.createOrder(cart, delivery, isShipped, new IRealmResponse<Boolean, Boolean>() {
            @Override
            public Boolean executeService(Boolean args) {
                return null;
            }
        });
    }
    public List<Order> getDataOrder(String idUser){
        orderDBHelper.getOrderByUser(idUser, new IRealmResponse<Boolean, List<OrderRealmModel>>() {
            @Override
            public Boolean executeService(List<OrderRealmModel> args) {
                if (args!=null){
                    for (OrderRealmModel orderRealmModel:args){
                        list.add(new Order(orderRealmModel.getIdOrder(),
                                orderRealmModel.getNameFruit(),
                                orderRealmModel.getQuantity(),
                                orderRealmModel.getTotalPrice(),
                                orderRealmModel.getDelivery(),
                                orderRealmModel.getShipped()));
                    }
                }
                return null;
            }
        });
        return list;
    }
    public void getCartData(int idCart, String idUser, IRealmResponse<Boolean, CartRealmModel> callback){
        cartDBHelper.getCartById(idUser, idCart, callback);
    }
    public void deleteCartItem(int cartID, String idUser, IRealmResponse<Boolean, Boolean> callback){
        cartDBHelper.deleteCartOnTop(cartID, idUser,callback);
    }
}
