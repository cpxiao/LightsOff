package com.cpxiao.lightsoff;

/**
 * Created by cpxiao on 5/15/16.
 * OnGameListener
 */
public interface OnGameListener {
    /**
     * 游戏成功
     */
    void onGameSuccess();

    /**
     * 步数改变
     *
     * @param steps 步数
     */
    void onStepsChange(int steps);

    /**
     * 游戏结束
     */
    void onGameOver();
}
