package com.example.demo.services;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;
import com.example.demo.repositories.OrderRepository;

@Service
public class OrderServices {

	@Autowired
	private OrderRepository orderRepository;

	public List<Orders> getOrders() {
		return this.orderRepository.findAll();
	}

	public void saveOrder(Orders order) {
		this.orderRepository.save(order);
	}

	public void updateOrder(int id, Orders order) {
		order.setoId(id);
		this.orderRepository.save(order);
	}

	public void deleteOrder(int id) {
		this.orderRepository.deleteById(id);
	}

	public List<Orders> getOrdersForUser(User user) {
		if (user == null || user.getU_id() == 0) {
			return Collections.emptyList();
		}
		return this.orderRepository.findOrdersByUserId(user.getU_id());
	}
}
