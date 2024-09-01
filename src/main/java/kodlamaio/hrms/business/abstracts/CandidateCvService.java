package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.utilities.results.DataResult;

public interface CandidateCvService {

	DataResult<List<Object>> getCandidateCvByCandidateId(int candidateId);
}
