import java.util.*;

public class LRU {
    static class Node{
        int key,val;
        Node next,prev;
        Node(int key,int val){
            this.key=key;
            this.val=val;
        }
    }
    static class LRUCache{
        int capacity;
        HashMap<Integer,Node> map;
        Node head,tail;
        public LRUCache(int capacity){
            this.capacity=capacity;
            map=new HashMap<>();
            head=new Node(0,0);
            tail=new Node(0,0);
            head.next=tail;
            tail.prev=head;
        }
        void add(Node node){
            node.next=head.next;
            node.prev=head;
            head.next.prev=node;
            head.next=node;
        }
        void remove(Node node){
            node.next.prev=node.prev;
            node.prev.next=node.next;

        }
        int get(int key){
            if(!map.containsKey(key)){
                return -1;
            }
            Node node=map.get(key);
            remove(node);
            add(node);
            return node.val;
        }
        void display(){
            Node curr = head.next;
            System.out.print("Data in cache: ");
            while(curr != tail){
                System.out.print("["+curr.key + ":" + curr.val + "] ");
                curr = curr.next;
            }
            System.out.println();
        }
        void put(int key, int val){
            if(map.containsKey(key)){
                Node node=map.get(key);
                node.val=val;
                remove(node);
                add(node);
                return;
            }
            Node newNode=new Node(key,val);
            map.put(key,newNode);
            add(newNode);
            if(map.size()>capacity){
                Node lru=tail.prev;
                remove(lru);
                System.out.println("Removed LRU key: " + lru.key);
                map.remove(lru.key);
            }
        }
    }

    public static void main(String []args){
        System.out.println("===== LRU CACHE DEMO =====");

        LRUCache cache = new LRUCache(3);

        System.out.println("\n===== PUT OPERATION =====");
        cache.put(1,10);
        cache.put(2,20);
        cache.put(3,30);
        cache.display();

        System.out.println("\n===== GET OPERATION =====");
        System.out.println("Get(1): " + cache.get(1));
        cache.display();

        System.out.println("\n===== LRU EVICTION =====");
        cache.put(4,40);
        cache.display();
    }
}
