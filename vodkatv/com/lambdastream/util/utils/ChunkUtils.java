package com.lambdastream.util.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChunkUtils {

    public static<T> ChunkTO<T> getChunk(Collection<T> collection,
            int startIndex, int count){

        /*
         * Get a list from the collection
         */
        List<T> list = new ArrayList<T>(collection);

        /*
         * Get the elements
         */
        boolean existsMore = false;
        if(startIndex > 0 && count > 0){
            int start = startIndex - 1;
            int finish = 0;

            if(startIndex + count - 1 < list.size()){
                existsMore = true;
                finish = startIndex + count - 1;
            } else {
                finish = list.size();
                existsMore = false;
            }
            if(start >= 0 && start <= finish) {
                /*
                 * subList returns a java.util.RandomAccessSubList, which is
                 * not Serializable. With this approach, we return an
                 * ArrayList, which is Serializable.
                 */
                list = new ArrayList<T>(list.subList(start, finish));
            } else {
                list = new ArrayList<T>();
                existsMore = false;
            }
        }

        /*
         * Return chunk
         */
        return new ChunkTO<T>(list, existsMore, collection.size());
    }


    public static<T> ExtendedChunkTO<T> getExtendedChunk(Collection<T> collection,
            int startIndex, int count){

        /*
         * Get a list from the collection
         */
        List<T> list = new ArrayList<T>();
        list.addAll(collection);

        /*
         * Get the elements
         */
        boolean existsMore = false;
        if(startIndex > 0 && count > 0){
            int start = startIndex - 1;
            int finish = 0;

            if(startIndex + count - 1 < list.size()){
                existsMore = true;
                finish = startIndex + count - 1;
            } else {
                finish = list.size();
                existsMore = false;
            }
            if(start >= 0 && start <= finish) {
                list = list.subList(start, finish);
            } else {
                list = new ArrayList<T>();
                existsMore = false;
            }
        }

        /*
         * Return chunk
         */
        return new ExtendedChunkTO<T>(
                list, existsMore, collection.size(), startIndex, count);
    }

}
