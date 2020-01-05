package ml.huytools.ycnanswer.Presenters.Interface;

public interface GamePresenter {
    void loadQuestions(int categoriesID);
    void answer(String answer);
    void support50();
    void supportSpectator();
    void supportCall();
    void lose();
}
