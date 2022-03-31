package com.gt.interpackage.handlers;

import java.util.Arrays;
import java.util.Optional;

import com.gt.interpackage.model.*;
import com.gt.interpackage.model.Package;
import com.gt.interpackage.service.*;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class QueueHandlerTest {

    @Mock
    private PackageService packageService;

    @Mock
    private QueueService queueService;

    @Mock
    private DestinationService destinationService;

    @Mock
    private CheckpointService checkpointService;

    @Mock
    private PackageCheckpointService packageCheckpointService;

    @Mock
    private RouteService routeService;

    private Queue queue;
    private Destination destination;
    private Route route;
    private Package aPackage;
    private Checkpoint checkpoint;
    private PackageCheckpoint packageCheckpoint;

    @InjectMocks
    private QueueHandler queueHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination("GT-PTN-1", "Guatemala-Peten", 15.50);
        route = new Route(1L, "Ruta 1", 10, 15, true, destination);
        checkpoint = new Checkpoint(1L, "PuntoControl1", 15.50, 15, 5, true, null, route);
        aPackage = new Package(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, 15.50, 15.50, Boolean.TRUE, "Descripcion ", null, 15.50, route, destination);
        aPackage.setDestination(destination);
        packageCheckpoint = new PackageCheckpoint(checkpoint, aPackage, null, true);
        queue = new Queue(1L, aPackage, 1);
    }

    @Test
    public void testVerifyQueue() throws Exception {
        Mockito.when(
                queueService
                        .findByDestination(ArgumentMatchers.any(Long.class)))
                .thenReturn(Arrays.asList(queue));
         Mockito.when(
                destinationService
                        .findAll())
                .thenReturn(Arrays.asList(destination));
        Mockito.when(
                checkpointService
                        .getAllCheckpointsByDestinationId(ArgumentMatchers.any(Long.class)))
                .thenReturn(Arrays.asList(checkpoint));
        Mockito.when(
                routeService
                        .getRouteById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(route));
        Mockito.when(
                routeService
                        .create(ArgumentMatchers.any(Route.class)))
                .thenReturn(route);
        Mockito.when(
                checkpointService
                        .create(ArgumentMatchers.any(Checkpoint.class)))
                .thenReturn(checkpoint);
        Mockito.when(
                packageService
                        .update(ArgumentMatchers.any(Package.class), ArgumentMatchers.any(Long.class)))
                .thenReturn(aPackage);
        queueHandler.verifiyQueue();
        //Mockito.verify(destinationService).findAll();
    }
}
