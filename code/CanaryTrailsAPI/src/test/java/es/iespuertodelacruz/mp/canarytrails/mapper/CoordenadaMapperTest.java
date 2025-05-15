package es.iespuertodelacruz.mp.canarytrails.mapper;

class CoordenadaMapperTest {

    /*private final CoordenadaMapper mapper = Mappers.getMapper(CoordenadaMapper.class);

    @Test
    void testEntityToDto() {
        Coordenada entity = new Coordenada();
        entity.setId(1);
        entity.setLatitud(new BigDecimal("28.123456"));
        entity.setLongitud(new BigDecimal("-16.654321"));

        CoordenadaDTO dto = mapper.toDTO(entity);
        assertEquals("28.123456", dto.getLatitud().toString());
    }

    @Test
    void testDtoToEntity() {
        CoordenadaDTO dto = new CoordenadaDTO(1, new BigDecimal("28.123456"), new BigDecimal("-16.654321"));
        Coordenada entity = mapper.toEntity(dto);
        assertEquals("-16.654321", entity.getLongitud().toString());
    }

    @Test
    void testListConversion() {
        CoordenadaDTO dto = new CoordenadaDTO(1, new BigDecimal("28.000000"), new BigDecimal("-15.000000"));
        List<Coordenada> entities = mapper.toEntityList(Collections.singletonList(dto));
        List<CoordenadaDTO> dtos = mapper.toDTOList(entities);

        assertEquals(1, dtos.size());
        assertEquals(dto.getLatitud(), dtos.get(0).getLatitud());
    }*/
}
