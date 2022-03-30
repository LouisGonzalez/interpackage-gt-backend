package com.gt.interpackage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gt.interpackage.model.PackageCheckpoint;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author helmuth
 */
@Repository
public interface PackageCheckpointRepository extends JpaRepository<PackageCheckpoint, Long>{

    public boolean existsPackageCheckpointByCheckpointId(Long id);
    
    /**
     * Metodo que consulta la tabla package-chekpoint de la base de datos para obtener 
     * una objeto de tipo PackageCheckpoint en base al id del paquete que se recibe como
     * parametro.
     * @param id
     * @return 
     */
    public PackageCheckpoint findByPackagesIdAndCurrentCheckpointTrue(Long id);
    
    
    /**
     * Metodo que obtiene de la base de datos la sumatoria de todos los tiempos en que un
     * paquete ha estado en los puntos de control de una ruta.
     * @param id
     * @return 
     */
    @Query(value="SELECT cast(sum(time_on_checkpoint) AS varchar) AS time FROM package_checkpoint WHERE id_package =?1", nativeQuery = true)
    public String getTimeOnRouteByPackageId(Long id);
    
    /**
     * Metodo que consulta la tabla package-chekpoint de la base de datos para obtener 
     * todos los  objetos de tipo PackageCheckpoint en base al id del punto de control
     * que se recibe como parametro y cuya columna current_checkpoint sea true.
     * @param id
     * @return 
     */
    public List<PackageCheckpoint> findAllByCheckpointIdAndCurrentCheckpointTrue(Long id);
    
    public PackageCheckpoint findByCheckpointIdAndPackagesId(Long checkpointId, Long packageId);
    
    @Transactional
    @Modifying
    @Query(value="UPDATE package_checkpoint SET current_checkpoint=?1, time_on_checkpoint=?2, date=?3 WHERE id_package=?4 AND id_checkpoint=?5", nativeQuery = true)
    public void update(Boolean currentCheckpoint, Time timeOnCheckpoint, Date date, Long idPackage, Long idCheckpoint);
    
    @Transactional
    @Modifying
    @Query(value="INSERT INTO package_checkpoint (current_checkpoint, id_package, id_checkpoint) VALUES(?1, ?2, ?3) ", nativeQuery = true)
    public void create(Boolean currentCheckpoint, Long packageId, Long checkpointId);
    
    @Query(value="SELECT c.id FROM checkpoint c WHERE c.id NOT IN("
                    + "SELECT id_checkpoint FROM package_checkpoint WHERE id_package = ?1)"
                    + "AND id_route  = ?2 ORDER BY c.id ASC LIMIT 1",
    nativeQuery = true)
    public Long getNextCheckpointId(Long packageId, Long idRoute);
    
}
