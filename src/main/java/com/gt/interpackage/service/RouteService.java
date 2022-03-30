package com.gt.interpackage.service;

import com.gt.interpackage.dto.TopRouteDTO;
import com.gt.interpackage.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gt.interpackage.model.Route;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
/**
 *
 * @author helmuth
 */
@Service
public class RouteService {
    
    @Autowired
    private RouteRepository routeRepository; 
    
    private final String QUERY_TOP_FILTER =    
            "SELECT COUNT(route.name) AS quantity, route.name AS route, destination.name AS destination "
            + "FROM route "
            + "INNER JOIN destination ON route.id_destination = destination.id "
            + "INNER JOIN package ON package.route = route.id "
            + "WHERE package.end_date BETWEEN :startDate AND :endDate "
            + "GROUP BY route.name, destination.name "
            + "ORDER BY quantity DESC "
            + "LIMIT 3 ";
    
    private final String QUERY_TOP =    
            "SELECT COUNT(route.name) AS quantity, route.name AS route, destination.name AS destination "
            + "FROM route "
            + "INNER JOIN destination ON route.id_destination = destination.id "
            + "INNER JOIN package ON package.route = route.id "
            + "GROUP BY route.name, destination.name "
            + "ORDER BY quantity DESC "
            + "LIMIT 3 ";
    
    /**
     * Metodo que llama al repositorio de rutas para crear una nueva ruta.
     * @param route
     * @return Ruta creada.
     */
    public Route create(Route route){
        return routeRepository.save(route);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para obtener todas aquellas 
     * rutas cuyo nombre inicie con el nombre que se recibe como parametro.
     * @param name
     * @return Listado de rutas obtenidas. 
     */
    public Optional<List<Route>> getAllByName(String name){
        return routeRepository.findByNameStartingWith(name);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para consultar si existe una
     * ruta cuyo nombre sea el  parametro que se recibe.
     * @param name
     * @return True o False. 
     */
    public boolean existsByName(String name){
        return routeRepository.existsRouteByName(name);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para consultar si existe una
     * ruta cuyo id sea el  parametro que se recibe.
     * @param id
     * @return True o False. 
     */
    public boolean existsById(Long id){
        return routeRepository.existsById(id);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para consultar si existe una
     * ruta cuyo nombre sea el  parametro que se recibe y cuyo id no sea el 
     * parametro que se recibe.
     * @param name
     * @param id
     * @return True o False. 
     */
    public boolean existsAndIdIsNot(String name, Long id){
        return routeRepository.existsRouteByNameAndIdIsNot(name, id);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para obtener todas 
     * las rutas y retornarlas en una paginacion. 
     * @param pageable
     * @return 
     */
    public Page<Route> getAll(Pageable pageable){
        return routeRepository.findAll(pageable);
    }
    
    public Page<Route> getRoutesByActive(Pageable pageable, Boolean active) {
        return routeRepository.findAllByActive(pageable, active);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para obtener una ruta 
     * cuyo id sea igual al que se recibe como parametro. 
     * @param id
     * @return 
     */
    public Optional<Route> getRouteById(Long id){
        return routeRepository.findById(id);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para actualizar 
     * la ruta que se recibe como parametro. 
     * @param route 
     */
    public Route update(Route route){
        return routeRepository.save(route);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para eliminar 
     * la ruta que se recibe como parametro. 
     * @param route 
     */
    public void delete(Route route){
        routeRepository.delete(route);
    }
    
    /**
     * Metodo que llama al repositorio de rutas para buscar
     * si existe una ruta cuyo id de destino sea igual al que se 
     * recibe como parametro.
     * @param destinationId 
     * @return  True | False
     */
    public boolean routeHasDestinationAssigned(Long destinationId){
        return routeRepository.existsRouteByDestinationId(destinationId);
    }
    
    public List<Route> getThreeRouteMostPopular() {
        return routeRepository.getThreeMostPopularRoute();
    }

    /*
     * Metodo que llama al repositorio de rutas para buscar
     * si existen rutas hacia un destino especifico
    */
    public List<Route> findRouteByDestination(Integer id_destination){
        return routeRepository.findRouteByDestination(id_destination);
    }
    
    /*
     * Metodo para obtener el top de rutas 
    */
    public List<TopRouteDTO> getTopRoute(EntityManager entityManager, String start, String end) throws ParseException {
        List<TopRouteDTO> lista;
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Query query = entityManager.createNativeQuery(QUERY_TOP_FILTER);
        if (end == null && start == null) {
            query = entityManager.createNativeQuery(QUERY_TOP);
            return query.getResultList();
        } 
        if (end == null) {
            Date startDate = dtf.parse(start);
            Calendar calendar = Calendar.getInstance();
            Date dateObj = calendar.getTime();
            query.setParameter("startDate", startDate, TemporalType.DATE);
            query.setParameter("endDate", dtf.format(dateObj));
        } else {
            Date startDate = dtf.parse(start);
            Date endDate = dtf.parse(end);
            System.out.println("StartDatet" + startDate);
            System.out.println("EndDatet" + endDate);
            query.setParameter("startDate", startDate, TemporalType.DATE);
            query.setParameter("endDate", endDate, TemporalType.DATE);
        }
        lista = query.getResultList();
        return lista;
    }
}
