package com.example.fuent.lispinterpreter.Adapters

import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fuent.lispinterpreter.*
import com.example.fuent.lispinterpreter.Carpetas.Companion.EXTRA_ARCHIVO_ID

class RecyclerViewAdaptadorArchivo: RecyclerView.Adapter<RecyclerViewAdaptadorArchivo.ViewHolder>{

    override fun getItemCount(): Int {
        return listaArchivos.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder{
        var name: TextView = itemView.findViewById(R.id.code_name)
        var author : TextView = itemView.findViewById(R.id.code_author)
        var view : View

        constructor(v: View) : super(v) {
            view = v
        }
    }

    var listaArchivos : ArrayList<Archivo>

    constructor(codeList: ArrayList<Archivo>){
        this.listaArchivos = codeList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_code,parent,false)
        return ViewHolder(view)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listaArchivos[position].nombre
        holder.author.text = listaArchivos[position].autor

        holder.view.setOnClickListener{
            var intent = Intent(holder.view.context, Editor :: class.java)
            intent.putExtra(EXTRA_ARCHIVO_ID,listaArchivos.get(position).script_id)
            holder.view.context.startActivity(intent)
        }
    }
}