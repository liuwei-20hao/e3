package com.e3.cart.controller;

import com.e3.cart.service.CartService;
import com.e3.pojo.*;
import com.e3.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
						  HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            cartService.addCart(user.getId(),itemId,num);
            //返回添加成功页面
            return "cartSuccess";
        }
		//从cookie中区商品信息
		List<TbItem> cartItems = getCartListFromCookie(request);
		//判断是否存在
		boolean flag = false;
		for(TbItem item:cartItems){
			if(item.getId() == itemId.longValue()){
				flag = true;
				//数量增加
				item.setNum(item.getNum() + num);
			}
		}
		if(!flag){
			TbItem t = itemService.getTbItemById(itemId);
			//设置商品数量
			t.setNum(num);
			//取一张图片
			String image = t.getImage();
			if (StringUtils.isNotBlank(image)) {
				t.setImage(image.split(",")[0]);
			}
			//把商品添加到商品列表
			cartItems.add(t);
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartItems), 432000, true);
		//返回添加成功页面
		return "cartSuccess";
	}

	private List<TbItem> getCartListFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, "cart", true);
		if(StringUtils.isEmpty(json)){
			return new ArrayList<>();
		}
		List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
		return  tbItems;
	}

	/**
	 * 展示购物车列表
	 * <p>Title: showCatList</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCatList(HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            //合并商品信息
            cartService.mergeCartList(user.getId(),cartList);
            //删除cookie中的商品信息
            CookieUtils.deleteCookie(request,response,"cart");
            //取购物车列表
            cartList = cartService.getCartList(user.getId());
        }
		//把列表传递给页面
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "cart";
	}

	/**
	 * 更新购物车商品数量
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
		 HttpServletRequest request ,HttpServletResponse response ){
		//判断用户是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.updateCartNum(user.getId(), itemId, num);
			return E3Result.ok();
		}
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历商品列表找到对应的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				//更新数量
				tbItem.setNum(num);
				break;
			}
		}
		//把购物车列表写回cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), 432000, true);
		//返回成功
		return E3Result.ok();
	}

	/**
	 * 删除购物车商品
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,
								 HttpServletResponse response) {
		//判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历列表，找到要删除的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				//删除商品
				cartList.remove(tbItem);
				//跳出循环
				break;
			}
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), 43200, true);
		//返回逻辑视图
		return "redirect:/cart/cart.html";
	}
}
