package com.ecommerce.productservice.grpc;

import com.ecommerce.grpc.InventoryRequest;
import com.ecommerce.grpc.InventoryResponse;
import com.ecommerce.grpc.InventoryServiceGrpc;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final ProductRepository productRepository;

    public InventoryGrpcService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void checkAndReserve(InventoryRequest request,
                                StreamObserver<InventoryResponse> responseObserver) {

        Long productId = request.getProductId();
        int quantity = request.getQuantity();

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            responseObserver.onNext(
                    InventoryResponse.newBuilder()
                            .setSuccess(false)
                            .setMessage("Product not found")
                            .build()
            );
            responseObserver.onCompleted();
            return;
        }

        if (product.getQuantity() < quantity) {
            responseObserver.onNext(
                    InventoryResponse.newBuilder()
                            .setSuccess(false)
                            .setMessage("Insufficient stock")
                            .build()
            );
            responseObserver.onCompleted();
            return;
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        responseObserver.onNext(
                InventoryResponse.newBuilder()
                        .setSuccess(true)
                        .setMessage("Stock reserved")
                        .build()
        );
        responseObserver.onCompleted();
    }
}