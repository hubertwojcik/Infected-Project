package models.Transport;

public interface TransportObserver {
    void onTransportPositionUpdate(Transport transport, int x, int y);
}