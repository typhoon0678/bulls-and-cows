package com.baseball.bullsandcows.service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baseball.bullsandcows.domain.Game;
import com.baseball.bullsandcows.domain.GameDTO;
import com.baseball.bullsandcows.domain.NumsMaker;
import com.baseball.bullsandcows.domain.RandomNumsMaker;
import com.baseball.bullsandcows.domain.User;
import com.baseball.bullsandcows.repository.GameRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GameService {


	private static final Logger log = LoggerFactory.getLogger(GameService.class);
	private final GameRepository gameRepository;
	private final UserRepository userRepository;

	public List<Game> findAll() {
		return gameRepository.findAll();
	}

	public Game findById(Long id) {
		return gameRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Game not found")
		);
	}

	public List<Game> getGameByUserId(Long userId) {
		return gameRepository.findAll().stream()
			.filter(game -> Objects.equals(game.getUser().getId(), userId))
			.collect(Collectors.toList());
	}

	public BestRankingResponse getBestRanking(Long userID) {

		List<Game> sortedGameList = SortGame.getRanking(gameRepository.findAll());

		int bestRecordIndex = IntStream.range(0, sortedGameList.size())
			.filter(i -> userID.equals(sortedGameList.get(i).getUser().getId()))
			.findFirst().orElse(-1);

		int bestScore = (bestRecordIndex != -1) ? sortedGameList.get(bestRecordIndex).getScore() : 0;
		return new BestRankingResponse(userID, bestRecordIndex + 1, bestScore);

	}

	public List<RankingListViewResponse> getRanking() {

		List<Game> sortedGameList = SortGame.getRanking(gameRepository.findAll());

		List<RankingListViewResponse> sortedRankingList = new ArrayList<>();

		for (Game game : sortedGameList) {
			String email = userRepository.findById(game.getUser().getId()).orElseThrow(() ->
				new IllegalArgumentException("user not found, id : " + game.getUser().getId())).getEmail();

			sortedRankingList.add(new RankingListViewResponse(
				email,
				game.getScore(),
				game.getPlayDate()
			));
		}

		return sortedRankingList;
	}

	private final GameRepository gameRepository;

	public GameDTO newGame(User user) {

		String randomNums = randomNumsToString();

		Game game = Game.builder()
			.user(user)
			.isFinished(false)
			.nums(randomNums)
			.tryCount(5)
			.score(0)
			.build();

		Game saveGame = gameRepository.save(game);
		return GameDTO.builder()
			.gameID(saveGame.getId())
			.userID(saveGame.getUser().getId())
			.tryCount(saveGame.getTryCount())
			.gameResult("continue")
			.build();

	}

	public GameDTO moveNextStage(User user, Long gameID) {

		Optional<Game> findGame = gameRepository.findByIdAndUser(gameID, user);
		Game game = findGame.orElseThrow(() -> new IllegalStateException("게임아이디와 유저아이디가 일치하지 않습니다."));
		String randomNums = randomNumsToString();

		int addScore = calculateStageScore(game.getTryCount());
		int result = gameRepository.updateNextStageGame(randomNums, addScore, gameID, user);

		Optional<Game> updateGame = Optional.empty();

		if(result > 0) {
			updateGame = gameRepository.findByIdAndUser(gameID, user);
		}

		Game game1 = updateGame.orElseThrow(() -> new IllegalStateException("[ERROR] 업데이트 오류"));

		return GameDTO.builder()
			.gameID(game1.getId())
			.userID(game1.getUser().getId())
			.tryCount(game1.getTryCount())
			.gameResult("continue")
			.build();

	}

	private int calculateStageScore(int tryCount) {
		return (tryCount + 1) * 100;
	}

	public GameDTO judgeGame(String inputNums, Long gameID, User user) {

		int result = gameRepository.judgeGameByIdAndUser(gameID, user);
		Optional<Game> findGame = Optional.empty();

		if(result > 0) {
			findGame = gameRepository.findByIdAndUser(gameID, user);
		}

		Game game = findGame.orElseThrow(() -> new IllegalStateException("게임아이디와 유저아이디가 일치하지 않습니다."));
		String outputNums = game.getNums();

		List<Integer> userNums = Arrays.stream(inputNums.split(""))
			.map(Integer::parseInt)
			.toList();
		List<Integer> gameNums = Arrays.stream(outputNums.split(""))
			.map(Integer::parseInt)
			.toList();

		int strikeCount = 0;
		int ballCount = 0;

		for (int i = 0; i < gameNums.size(); i++) {

			int eachUserNum = userNums.get(i);

			if (eachUserNum == gameNums.get(i)) {
				strikeCount++;
			} else if (gameNums.contains(eachUserNum)) {
				ballCount++;
			}
		}

		GameDTO gameDTO = GameDTO.builder()
			.gameID(game.getId())
			.userID(game.getUser().getId())
			.tryCount(game.getTryCount())
			.judgement(List.of(strikeCount, ballCount))
			.build();

		if (strikeCount == 3) {

			gameDTO.setGameResult("next");
			gameDTO.setAddScore(calculateStageScore(game.getTryCount()));
			gameDTO.setTotalScore(game.getScore());

		} else if (game.getTryCount() <= 0) {
			gameDTO.setGameResult("gameOver");
			gameDTO.setTotalScore(game.getScore());
		} else {
			gameDTO.setGameResult("continue");
		}

		return gameDTO;
	}

	public List<Game> findAll() {

		return gameRepository.findAll();
	}

	private String randomNumsToString() {
		NumsMaker numsMaker = new RandomNumsMaker();
		List<Integer> randomNums = numsMaker.make(1, 9, 3);

		StringBuilder stringBuffer = new StringBuilder();
		for (Integer randomNum : randomNums) {
			stringBuffer.append(randomNum);
		}

		return stringBuffer.toString();
	}


}
