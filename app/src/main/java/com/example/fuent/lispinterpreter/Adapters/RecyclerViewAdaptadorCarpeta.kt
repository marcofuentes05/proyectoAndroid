package com.example.fuent.lispinterpreter.Adapters

import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fuent.lispinterpreter.Archivos
import com.example.fuent.lispinterpreter.Carpeta
import com.example.fuent.lispinterpreter.Carpetas.Companion.EXTRA_CARPETA_ID
import com.example.fuent.lispinterpreter.MyApplication
import com.example.fuent.lispinterpreter.R

class RecyclerViewAdaptadorCarpeta : RecyclerView.Adapter<RecyclerViewAdaptadorCarpeta.ViewHolder> {
    override fun getItemCount(): Int {
        return listaCarpetas.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder{
        var name: TextView = itemView.findViewById(R.id.n)
        var author : TextView = itemView.findViewById(R.id.a)
        //var id : String = ""
        var view : View

        constructor(v: View) : super(v) {
            view = v
        }
    }

    var listaCarpetas : ArrayList<Carpeta>

    constructor(codeList: ArrayList<Carpeta>){
        this.listaCarpetas = codeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_carpeta,parent,false)
        return ViewHolder(view)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listaCarpetas[position].nombre
        holder.author.text = listaCarpetas[position].autor

        holder.view.setOnClickListener{
            var intent = Intent(holder.view.context, Archivos :: class.java)
            intent.putExtra(EXTRA_CARPETA_ID, listaCarpetas[position].id)
            holder.view.context.startActivity(intent)
        }
    }

}