package com.digivault.digihotel.service;

public class HotelServiceResponse<T> {

    private final T payLoad;
    private final boolean success;
    private final AdditionalInformation additionalInformation;

    public HotelServiceResponse(T payLoad, boolean success, AdditionalInformation error) {
        this.payLoad = payLoad;
        this.success = success;
        this.additionalInformation = error;
    }

    public T getPayLoad() {
        return payLoad;
    }

    public boolean isSuccess() {
        return success;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }
}
