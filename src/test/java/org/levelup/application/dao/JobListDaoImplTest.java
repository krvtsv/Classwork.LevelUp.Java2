package org.levelup.application.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.levelup.application.domain.JobListEntity;
import org.levelup.application.domain.JobListId;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JobListDaoImplTest {
    @Mock
    private SessionFactory factory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private JobListDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(factory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
    }

    @AfterEach
    void tearDown() {
        dao = null;
    }

    @Test
    void testCreateJobRecord_whenValidParams_thenCreateJobRecord() throws NoSuchFieldException, IllegalAccessException {
        Integer companyId = 2;
        Integer userId = 4;
        Integer positionID = 16;
        LocalDate startDate = LocalDate.of(2018, 12, 10);
        LocalDate endDate = LocalDate.of(2019, 9, 28);

        JobListEntity entity = dao.createJobRecord(companyId, userId, positionID, startDate, endDate);
        JobListId jobListId = entity.getId();

        //checking id using reflection
        Class<?> JobListIdClass = jobListId.getClass();
        JobListIdClass.getFields();
        Field userIdField = JobListIdClass.getDeclaredField("userId");
        Field companyIdField = JobListIdClass.getDeclaredField("companyId");
        Field positionIdField = JobListIdClass.getDeclaredField("positionId");
        userIdField.setAccessible(true);
        companyIdField.setAccessible(true);
        positionIdField.setAccessible(true);

        assertEquals(companyId, companyIdField.get(jobListId));
        assertEquals(userId, userIdField.get(jobListId));
        assertEquals(positionID, positionIdField.get(jobListId));
        assertEquals(startDate, entity.getStartDate());
        assertEquals(endDate, entity.getEndDate());

        verify(session).persist(entity);
        verify (transaction).commit ();
        verify(session).close();
    }

    @Test
    void testFindJobRecord_whenValidParams_thenFindJobRecord() throws NoSuchFieldException, IllegalAccessException {
        Integer companyId = 2;
        Integer userId = 4;
        Integer positionID = 16;
        LocalDate startDate = LocalDate.of(2018, 12, 10);

        when(session.get(eq(JobListEntity.class), any(Serializable.class))).thenReturn(new JobListEntity(new JobListId(companyId,positionID,userId),startDate));
        JobListEntity entity = dao.findJobRecord(companyId, userId, positionID);
        JobListId jobListId = entity.getId();

        //checking id using reflection
        Class<?> JobListIdClass = jobListId.getClass();
        JobListIdClass.getFields();
        Field userIdField = JobListIdClass.getDeclaredField("userId");
        Field companyIdField = JobListIdClass.getDeclaredField("companyId");
        Field positionIdField = JobListIdClass.getDeclaredField("positionId");
        userIdField.setAccessible(true);
        companyIdField.setAccessible(true);
        positionIdField.setAccessible(true);

        assertEquals(companyId, companyIdField.get(jobListId));
        assertEquals(userId, userIdField.get(jobListId));
        assertEquals(positionID, positionIdField.get(jobListId));

        verify(session).get(eq(JobListEntity.class), any(Serializable.class));
        verify(session).close();
    }
}