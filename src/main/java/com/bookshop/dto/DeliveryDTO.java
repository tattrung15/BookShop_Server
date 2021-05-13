package com.bookshop.dto;

public class DeliveryDTO {
    private Long deliveryId;

    public DeliveryDTO() {
    }

    public DeliveryDTO(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
}
