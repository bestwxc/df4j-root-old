package net.df.base.utils;
import java.util.List;

/**
 * 二进制位运算工具类
 */
public class BitUtils {

    /**
     * 将序号为index设置为1
     * @param fromState
     * @param index
     * @return
     */
    public static long setYes(long fromState, int index){
        return fromState | (1 << index);
    }

    /**
     * 将序号为index设置为0
     * @param fromState
     * @param index
     * @return
     */
    public static long setNo(long fromState, int index){
        return fromState & ~ (1 << index);
    }

    /**
     * 将fromIndex（包含）至toIndex（不包含） 所有的位置为1
     * @param fromState
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public static long setYes(long fromState, int fromIndex, int toIndex){
        return fromState | getState(fromIndex, toIndex);
    }

    /**
     * 将fromIndex（包含）至toIndex（不包含） 所有的位置为0
     * @param fromState
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public static long setNo(long fromState, int fromIndex, int toIndex){
        return fromState & ~ getState(fromIndex, toIndex);
    }


    /**
     * 将从index（包含）至最高位置为0
     * @param fromState
     * @param index
     * @return
     */
    public static long clearFlag(long fromState, int index){
        return fromState & ~getState(index);
    }

    /**
     * 获取一个二进制数，从fromIndex到toIndex全部为1
     * 如从3到6全为1 得到0011 1000，即56
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public static long getState(int fromIndex, int toIndex){
        return ((1 << (toIndex - fromIndex)) - 1) << fromIndex;
    }

    /**
     * 获取一个二进制数，从index到最高位全为1
     * @param index
     * @return
     */
    public static long getState(int index){
        return ~getState(0, index);
    }

    /**
     * 将序号为index设置为flag
     * @param fromState
     * @param index
     * @param flag
     * @return
     */
    public static long setFlag(long fromState, int index, int flag){
        return flag == 0 ? setNo(fromState, index) : setYes(fromState, index);
    }

    /**
     * 获取序号为index的状态位
     * @param fromState
     * @param index
     * @return
     */
    public static long getFlag(long fromState, int index){
        return (fromState >> index) & 1L;
    }

    /**
     * 按照selectAndOrder指定的规则转换状态位。
     * @param fromState
     * @param selectAndOrder
     * @return
     */
    public static long changeOrder(long fromState, List<Integer> selectAndOrder){
        long toState = 0L;
        for(int i = 0; i < selectAndOrder.size(); i++){
            if(ValidateUtils.notNull(selectAndOrder.get(i))){
                toState = toState | (((fromState >> selectAndOrder.get(i)) & 1) << i);
            }
        }
        return toState;
    }
}
