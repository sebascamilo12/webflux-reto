package co.com.sofka.api;

import co.com.sofka.model.galleta.Galleta;
import lombok.RequiredArgsConstructor;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Handler {
//private  final UseCase useCase;
//private  final UseCase2 useCase2;
    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // usecase.logic();
        return ServerResponse.ok().body(listCookies(), Flux.class);
    }

    public Mono<ServerResponse> listenGETByTipo(ServerRequest serverRequest) {
        Integer codigo = Integer.parseInt(serverRequest.queryParam("codigo").orElse("-1"));
        return ServerResponse.ok().body(tipoByCookie(codigo), Flux.class);
    }


    private Flux<Galleta> listCookies(){

        return listar();
    }

    private Mono<Galleta> tipoByCookie(Integer codigo){
        System.out.println(codigo);
        Galleta galleta= new Galleta(0,"No existe");
        return listar()
                .filter(data -> data.getCodigo()==codigo)
                .defaultIfEmpty(galleta)
                .reduce((galleta1, galleta2) -> new Galleta(galleta1.getCodigo(), galleta1.getTipo()))
                .onErrorResume(error -> {
                    System.out.println("No se encontro el tipo de galleta " + error.getMessage());
                    return Mono.just(galleta);
                });
    }

    private Flux<Galleta> listar(){
        List<Galleta> galletas = new ArrayList<>();
        galletas.add(new Galleta(1, "Chocolate"));
        galletas.add(new Galleta(2, "Coco"));
        galletas.add(new Galleta(3, "Hojaldre"));
        galletas.add(new Galleta(4, "Arequipe"));
        galletas.add(new Galleta(5, "Avena"));
        galletas.add(new Galleta(6, "Flauta"));
        galletas.add(new Galleta(7, "Mariposa"));
        galletas.add(new Galleta(8, "Brownie"));
        galletas.add(new Galleta(9, "Animalito"));
        galletas.add(new Galleta(10, "Tostadas"));

        return Flux.fromIterable(galletas);

    }
}
