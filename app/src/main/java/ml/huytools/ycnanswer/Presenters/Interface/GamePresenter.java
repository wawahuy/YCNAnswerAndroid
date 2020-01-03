package ml.huytools.ycnanswer.Presenters.Interface;

public interface GamePresenter {
    void loadQuestions(int categoriesID);
    void answer(String answer);
    void lose();
}
