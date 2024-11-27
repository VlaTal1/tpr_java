package org.example.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.service.AuctionService;

@GrpcService
@RequiredArgsConstructor
public class AuctionGrpcService extends AuctionServiceGrpc.AuctionServiceImplBase {

    private final AuctionService auctionService;

    @Override
    public void createAuction(CreateAuctionRequest request, StreamObserver<CreateAuctionResponse> responseObserver) {
        try {
            Auction auction = new Auction();
            auction.setVehicleId(Long.parseLong(request.getVehicleId()));
            auction.setName(request.getName());
            auction.setBidTimeoutSec(request.getBidTimeoutSec());
            auction.setStartPrice(request.getStartPrice());
            auction.setMinBid(request.getMinBid());
            auction.setAuctionStatus(AuctionStatus.valueOf(request.getAuctionStatus()));

            Auction createdAuction = auctionService.create(auction);

            CreateAuctionResponse response = CreateAuctionResponse.newBuilder()
                    .setAuctionId(createdAuction.getId())
                    .setName(createdAuction.getName())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Обработка исключений
            responseObserver.onError(e);
        }
    }

    @Override
    public void startAuction(StartAuctionRequest request, StreamObserver<StartAuctionResponse> responseObserver) {
        try {
            auctionService.startAuction(request.getAuctionId());

            StartAuctionResponse response = StartAuctionResponse.newBuilder()
                    .setStatus("Auction started")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Обработка исключений
            responseObserver.onError(e);
        }
    }
}
