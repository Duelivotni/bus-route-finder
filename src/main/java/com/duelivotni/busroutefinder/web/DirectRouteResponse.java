package com.duelivotni.busroutefinder.web;

class DirectRouteResponse {

    private final Integer from;
    private final Integer to;
    private final boolean direct;

    public DirectRouteResponse(Integer from, Integer to, boolean direct) {
        this.from = from;
        this.to = to;
        this.direct = direct;
    }

    public Integer getDepSid() {
        return from;
    }

    public Integer getArrSid() {
        return to;
    }

    public boolean isDirectBusRoute() {
        return direct;
    }
}
