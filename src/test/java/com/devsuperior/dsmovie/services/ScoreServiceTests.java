package com.devsuperior.dsmovie.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;
	
	@Mock
	private ScoreRepository repository;
	
	@Mock
	private MovieRepository movieRepository;
	
	@Mock
	private UserService userService;
	
	private MovieEntity movie;
	private ScoreDTO scoreDTO;
	private ScoreEntity score;
	private UserEntity user;
	private Long nonExistingMovieId;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		nonExistingMovieId = 1000L;
		
		score = ScoreFactory.createScoreEntity();
        scoreDTO = ScoreFactory.createScoreDTO();
		
		movie = MovieFactory.createMovieEntity();
		
		 user = UserFactory.createUserEntity();
		
		Mockito.when(userService.authenticated()).thenReturn(user);
		
		
        Mockito.when(movieRepository.findById(eq(scoreDTO.getMovieId()))).thenReturn(Optional.of(movie));

       
        Mockito.when(repository.saveAndFlush(any(ScoreEntity.class))).thenReturn(score);

        Mockito.when(movieRepository.save(any(MovieEntity.class))).thenReturn(movie);
	}
	
	@Test
	public void saveScoreShouldReturnMovieDTO() {
		
		MovieDTO result = service.saveScore(scoreDTO);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		
		ScoreDTO invalidScoreDTO = new ScoreDTO(nonExistingMovieId, 5.0);
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
		
			service.saveScore(invalidScoreDTO);
		});
		
	}
}
