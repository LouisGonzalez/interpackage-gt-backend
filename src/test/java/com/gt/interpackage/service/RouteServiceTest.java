package com.gt.interpackage.service;


import com.gt.interpackage.model.Destination;
import com.gt.interpackage.model.Route;
import com.gt.interpackage.repository.RouteRepository;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author bryan
 */
public class RouteServiceTest {

    @InjectMocks
    private RouteService routeService;
    
    @Mock
    private RouteRepository routeRepository;
       
    @Mock
    private Route route;
    
    @Mock
    private Destination destination;
    
    @Mock
    private Page<Route> page;
    
    @Mock
    private Pageable pageable;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        destination = new Destination(1L, "GUATEMALA-PETEN", "De Gautemala a Peten", 52.50);
        route = new Route(1L, "Ruta 1", 45, 150, true, destination);
        pageable = PageRequest.of(1, 10);
    }
    
    @Test
    public void testGetRoutesByActive() {
        System.out.println("RouteServiceTest - getRoutesByActive");
        Mockito.when(
                routeRepository
                        .findAllByActive(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Boolean.class)))
                .thenReturn(Page.empty());
        Page<Route> page =routeService.getRoutesByActive(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Boolean.class));
        assertNotNull(Page.empty());
    }
    
    @Test
    public void testGetTopRoute() throws ParseException{
         System.out.println("RouteServiceTest - getTopRoute");
         Mockito.when(
                 routeRepository
                         .getThreeMostPopularRoute())
                 .thenReturn(Arrays.asList(route));
         assertNotNull(routeService.getThreeRouteMostPopular());
    }
    
    @Test
    public void testCreate(){
        System.out.println("RouteServiceTest - create");
        Mockito.when(routeRepository.save(Mockito.any(Route.class))).thenReturn(route);
        assertNotNull(routeService.create(route));
    }
    
    @Test
    public void testGetAllByName(){
        System.out.println("RouteServiceTest - getAllByName");
        Mockito.when(routeRepository.findByNameStartingWith(Mockito.anyString())).thenReturn(Optional.of(Arrays.asList(route)));
        assertNotNull(routeService.getAllByName(Mockito.anyString()));
        assertEquals(route, routeService.getAllByName("R").get().get(0));
    }
    
    @Test
    public void testExistsByName(){
        System.out.println("RouteServiceTest - existsByName");
        Mockito.when(routeRepository.existsRouteByName("R")).thenReturn(Boolean.TRUE);
        assertNotNull(routeService.existsByName("R"));
        assertTrue(routeService.existsByName("R"));
        
    }
    
    @Test
    public void testExistsById(){
        System.out.println("RouteServiceTest - existsById");
        Mockito.when(routeRepository.existsById(Mockito.anyLong())).thenReturn(Boolean.FALSE, Boolean.TRUE);
        assertNotNull(routeService.existsById(Mockito.anyLong()));
    }
    
    @Test
    public void testGetAll(){
        System.out.println("RouteServiceTest - getAll");
        Mockito.when(routeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        assertNotNull(routeService.getAll(pageable));
    }
    
    @Test
    public void testGetRouteById(){
        System.out.println("RouteServiceTest - getRouteById");
        Mockito.when(routeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(route));
        assertNotNull(routeService.getRouteById(Mockito.anyLong()));
    }
    
    @Test
    public void testUpdate(){
        System.out.println("RouteServiceTest - update");
        Mockito.when(routeRepository.save(Mockito.any(Route.class))).thenReturn(route);
        assertNotNull(routeService.update(route));
    }
    
    @Test
    public void testRouteHasDestinationAssigned(){
        System.out.println("RouteServiceTest - routeHasDestinationAssigned");
        Mockito.when(routeRepository.existsRouteByDestinationId(Mockito.anyLong())).thenReturn(Boolean.FALSE, Boolean.TRUE);
        assertNotNull(routeRepository.existsRouteByDestinationId(Mockito.anyLong()));
    }
    
}