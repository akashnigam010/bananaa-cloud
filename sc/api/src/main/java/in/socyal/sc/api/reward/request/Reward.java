package in.socyal.sc.api.reward.request;

import java.io.Serializable;

public class Reward implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer quantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
