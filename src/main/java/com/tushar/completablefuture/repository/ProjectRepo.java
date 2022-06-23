package com.tushar.completablefuture.repository;

import com.tushar.completablefuture.exception.CustomException;
import com.tushar.completablefuture.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class ProjectRepo {
    public static final String GET_PROJECT_BY_ID = "SELECT * FROM Project WHERE projectId=:projectId";
    public static final String GET_ALL_PROJECTS = "SELECT * FROM Project";

    @Autowired
    private NamedParameterJdbcTemplate dbRepo;

    @Async
    public CompletableFuture<List<Project>> getAllProjects(){
        List<Project> projectList = null;
        try{
            projectList = dbRepo.query(GET_ALL_PROJECTS,
                    new MapSqlParameterSource(),BeanPropertyRowMapper.newInstance(Project.class));
        }catch (Exception e){
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(projectList);
    }
    @Async
    public CompletableFuture<Project> getProjectById(Integer projectId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("projectId", projectId);
        Project project;
        try {
            project = dbRepo.queryForObject(GET_PROJECT_BY_ID,
                    parameterSource, BeanPropertyRowMapper.newInstance(Project.class));
        } catch (EmptyResultDataAccessException e) {
            // if specified project id does not exist
            throw new CustomException("No record exist for projectId - " + projectId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CompletableFuture.completedFuture(project);
    }

    public Project getProjectByProjectId(Integer projectId){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("projectId", projectId);
        Project project;
        try {
            project = dbRepo.queryForObject(GET_PROJECT_BY_ID,
                    parameterSource, BeanPropertyRowMapper.newInstance(Project.class));
        } catch (EmptyResultDataAccessException e) {
            // if specified project id does not exist
            throw new CustomException("No record exist for projectId - " + projectId, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return project;
    }
}
