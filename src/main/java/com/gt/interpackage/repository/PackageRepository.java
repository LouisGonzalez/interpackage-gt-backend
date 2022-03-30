package com.gt.interpackage.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gt.interpackage.model.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Luis
 */
@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    
    @Query(value = "SELECT * FROM package p WHERE p.at_destination = true AND p.retired = false", nativeQuery = true)
    List<Package> getInDestination();
    
    /**
     * Metodo que retorna desde la base de datos paginacion con todos aquellos
     * paquetes que se encuentran en destino y que aun no han sido retirados.
     * @param pageable
     * @return 
     */
    public Page<Package> findAllByAtDestinationTrueAndRetiredFalse(Pageable pageable);
    
    /**
     * Metodo que retorna desde la base de datos paginacion con todos aquellos
     * paquetes que se encuentran en ruta.
     * @param pageable
     * @return 
     */
    public Page<Package> findAllByOnWayTrue(Pageable pageable);
    
    /**
     * Metodo que retorna desde la base de datos el paquete cuyo id
     * de factura sea igual al parametro que se recibe.
     * @param id
     * @return 
     */
    public List<Package> findAllByInvoiceIdAndOnWayTrue(Long id);

    @Query(value = "SELECT * FROM package p WHERE p.id_invoice = ?1", nativeQuery = true)
    List<Package> getPackagesByInvoice(Long id_invoice);
    
}
