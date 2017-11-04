import java.util.*;
import java.lang.*;

public class Worker extends Thread{
	int[] array;
	int l, r;

	public Worker(int[] a, int l, int r){
		this.l=l;
		this.r=r;
		array=a;
	}

	public void merge(int[] arr, int l, int m, int r){
		int n1=m-l+1;
		int n2=r-m;
		int a[]=new int[n1];
		int b[]=new int[n2];
		for(int i=0; i<n1; ++i)
			a[i]=arr[l+i];
		for(int i=0; i<n2; ++i)
			b[i]=arr[m+1+i];
		int i=0, j=0, k=l;
		while(i<n1 && j<n2){
			if(a[i]<=b[j]){
				arr[k]=a[i];
				i++;
			}
			else{
				arr[k]=b[j];
				j++;
			}
			k++;
		}
		while(i<n1){
			arr[k]=a[i];
			k++;
			i++;
		}
		while(j<n2){
			arr[k]=b[j];
			k++;
			j++;
		}
	}

	public int[] getArray(){
		return array;
	}

	void mergesort(int a[], int l, int r){
		if(l<r){
			int m=(l+r)/2;
			mergesort(a, l, m);
			mergesort(a, m+1, r);
			merge(a, l, m, r);
		}
	}

	public void run(){
		try{
			if(l<r){
				int m=(l+r)/2;
				Worker t1=new Worker(array, l, m);
				t1.start();
				t1.join();
				Worker t2=new Worker(array, m+1, r);
				t2.start();
				t2.join();
				merge(array, l, m, r);
			}
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}