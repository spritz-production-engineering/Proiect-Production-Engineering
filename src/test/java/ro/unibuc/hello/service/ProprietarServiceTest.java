package ro.unibuc.hello.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ro.unibuc.hello.data.ProprietarEntity;
import ro.unibuc.hello.data.ProprietarRepository;
import ro.unibuc.hello.dto.Proprietar;

import static org.mockito.Mockito.*;

public class ProprietarServiceTest {

    @Mock
    private ProprietarRepository proprietarRepository;

    @Mock
    private MeterRegistry meterRegistry;

    @InjectMocks
    private ProprietarService proprietarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProprietarIncrementsMetric() {
        Proprietar p = new Proprietar(null, "Test", "User", "test@email.com", "1234567890123");
        ProprietarEntity savedEntity = new ProprietarEntity();
        savedEntity.setId("1");

        Counter counterMock = Mockito.mock(Counter.class);
        when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(counterMock);
        when(proprietarRepository.save(any())).thenReturn(savedEntity);

        proprietarService.createProprietar(p);

        verify(counterMock, times(1)).increment();
    }
}
