package com.developers.service.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developers.dto.CrimeDTO;
import com.developers.exception.ErrorDto;
import com.developers.exception.NotFoundException;
import com.developers.model.Crime;
import com.developers.repository.CrimeRepository;
import com.developers.service.iface.ICrimeService;
import com.developers.util.ConstantesUtil;

@Service
public class CrimeServiceImpl implements ICrimeService {
	
	@Autowired private CrimeRepository crimeRepositorio;

	@Override
	@Transactional(readOnly = true) // readOnly- solo consulta
	public List<CrimeDTO> findAll() {
		List<Crime> crimes = (List<Crime>) crimeRepositorio.findAll();
		List<CrimeDTO> crimesDto = new ArrayList<CrimeDTO>();
		crimes.stream().forEach(cr -> {
			CrimeDTO crimeDto = new CrimeDTO();
			crimeDto.setId(cr.getIdCrime());
			crimeDto.setName(cr.getNameCrime());
			crimeDto.setDescription(cr.getDescriptionCrime());
			crimesDto.add(crimeDto);
		});
		return crimesDto;
	}

	@Override
	@Transactional(readOnly = true) 
	public Crime findById(Long id) {
		return crimeRepositorio.findById(id).orElse(null);
	}

	@Override
	@Transactional 
	public Crime save(Crime crime) {
		return crimeRepositorio.save(crime);
	}

	@Override
	@Transactional
	public void delete(Long id) throws NotFoundException {
		Optional<Crime>	crime = crimeRepositorio.findById(id);
		if(crime.isPresent()) {
			crimeRepositorio.deleteById(id);
		}else {
			throw new NotFoundException(ErrorDto.getErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(), ConstantesUtil.MESSAGE_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
		}
	}

}
