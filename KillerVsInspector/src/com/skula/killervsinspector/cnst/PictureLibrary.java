package com.skula.killervsinspector.cnst;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.skula.killervsinspector.R;

public class PictureLibrary {
	private Map<Integer, Bitmap> map;

	@SuppressLint("UseSparseArrays")
	public PictureLibrary(Resources res) {
		this.map = new HashMap<Integer, Bitmap>();
		this.map.put(R.drawable.ernest_suspect, BitmapFactory.decodeResource(res, R.drawable.ernest_suspect));
		this.map.put(R.drawable.franklin_suspect, BitmapFactory.decodeResource(res, R.drawable.franklin_suspect));
		this.map.put(R.drawable.phoebe_suspect, BitmapFactory.decodeResource(res, R.drawable.phoebe_suspect));
		this.map.put(R.drawable.horatio_suspect, BitmapFactory.decodeResource(res, R.drawable.horatio_suspect));
		this.map.put(R.drawable.kristoph_suspect, BitmapFactory.decodeResource(res, R.drawable.kristoph_suspect));
		this.map.put(R.drawable.ryan_suspect, BitmapFactory.decodeResource(res, R.drawable.ryan_suspect));
		this.map.put(R.drawable.udstad_suspect, BitmapFactory.decodeResource(res, R.drawable.udstad_suspect));
		this.map.put(R.drawable.deidre_suspect, BitmapFactory.decodeResource(res, R.drawable.deidre_suspect));
		this.map.put(R.drawable.linus_suspect, BitmapFactory.decodeResource(res, R.drawable.linus_suspect));
		this.map.put(R.drawable.quinton_suspect, BitmapFactory.decodeResource(res, R.drawable.quinton_suspect));
		this.map.put(R.drawable.julian_suspect, BitmapFactory.decodeResource(res, R.drawable.julian_suspect));
		this.map.put(R.drawable.alyss_suspect, BitmapFactory.decodeResource(res, R.drawable.alyss_suspect));
		this.map.put(R.drawable.barrin_suspect, BitmapFactory.decodeResource(res, R.drawable.barrin_suspect));
		this.map.put(R.drawable.clive_suspect, BitmapFactory.decodeResource(res, R.drawable.clive_suspect));
		this.map.put(R.drawable.irma_suspect, BitmapFactory.decodeResource(res, R.drawable.irma_suspect));
		this.map.put(R.drawable.trevor_suspect, BitmapFactory.decodeResource(res, R.drawable.trevor_suspect));
		this.map.put(R.drawable.vladimir_suspect, BitmapFactory.decodeResource(res, R.drawable.vladimir_suspect));
		this.map.put(R.drawable.marion_suspect, BitmapFactory.decodeResource(res, R.drawable.marion_suspect));
		this.map.put(R.drawable.niel_suspect, BitmapFactory.decodeResource(res, R.drawable.niel_suspect));
		this.map.put(R.drawable.wilhelm_suspect, BitmapFactory.decodeResource(res, R.drawable.wilhelm_suspect));
		this.map.put(R.drawable.zachary_suspect, BitmapFactory.decodeResource(res, R.drawable.zachary_suspect));
		this.map.put(R.drawable.geneva_suspect, BitmapFactory.decodeResource(res, R.drawable.geneva_suspect));
		this.map.put(R.drawable.yvonne_suspect, BitmapFactory.decodeResource(res, R.drawable.yvonne_suspect));
		this.map.put(R.drawable.simon_suspect, BitmapFactory.decodeResource(res, R.drawable.simon_suspect));
		this.map.put(R.drawable.ophelia_suspect, BitmapFactory.decodeResource(res, R.drawable.ophelia_suspect));

		this.map.put(R.drawable.ernest_offside, BitmapFactory.decodeResource(res, R.drawable.ernest_offside));
		this.map.put(R.drawable.franklin_offside, BitmapFactory.decodeResource(res, R.drawable.franklin_offside));
		this.map.put(R.drawable.phoebe_offside, BitmapFactory.decodeResource(res, R.drawable.phoebe_offside));
		this.map.put(R.drawable.horatio_offside, BitmapFactory.decodeResource(res, R.drawable.horatio_offside));
		this.map.put(R.drawable.kristoph_offside, BitmapFactory.decodeResource(res, R.drawable.kristoph_offside));
		this.map.put(R.drawable.ryan_offside, BitmapFactory.decodeResource(res, R.drawable.ryan_offside));
		this.map.put(R.drawable.udstad_offside, BitmapFactory.decodeResource(res, R.drawable.udstad_offside));
		this.map.put(R.drawable.deidre_offside, BitmapFactory.decodeResource(res, R.drawable.deidre_offside));
		this.map.put(R.drawable.linus_offside, BitmapFactory.decodeResource(res, R.drawable.linus_offside));
		this.map.put(R.drawable.quinton_offside, BitmapFactory.decodeResource(res, R.drawable.quinton_offside));
		this.map.put(R.drawable.julian_offside, BitmapFactory.decodeResource(res, R.drawable.julian_offside));
		this.map.put(R.drawable.alyss_offside, BitmapFactory.decodeResource(res, R.drawable.alyss_offside));
		this.map.put(R.drawable.barrin_offside, BitmapFactory.decodeResource(res, R.drawable.barrin_offside));
		this.map.put(R.drawable.clive_offside, BitmapFactory.decodeResource(res, R.drawable.clive_offside));
		this.map.put(R.drawable.irma_offside, BitmapFactory.decodeResource(res, R.drawable.irma_offside));
		this.map.put(R.drawable.trevor_offside, BitmapFactory.decodeResource(res, R.drawable.trevor_offside));
		this.map.put(R.drawable.vladimir_offside, BitmapFactory.decodeResource(res, R.drawable.vladimir_offside));
		this.map.put(R.drawable.marion_offside, BitmapFactory.decodeResource(res, R.drawable.marion_offside));
		this.map.put(R.drawable.niel_offside, BitmapFactory.decodeResource(res, R.drawable.niel_offside));
		this.map.put(R.drawable.wilhelm_offside, BitmapFactory.decodeResource(res, R.drawable.wilhelm_offside));
		this.map.put(R.drawable.zachary_offside, BitmapFactory.decodeResource(res, R.drawable.zachary_offside));
		this.map.put(R.drawable.geneva_offside, BitmapFactory.decodeResource(res, R.drawable.geneva_offside));
		this.map.put(R.drawable.yvonne_offside, BitmapFactory.decodeResource(res, R.drawable.yvonne_offside));
		this.map.put(R.drawable.simon_offside, BitmapFactory.decodeResource(res, R.drawable.simon_offside));
		this.map.put(R.drawable.ophelia_offside, BitmapFactory.decodeResource(res, R.drawable.ophelia_offside));

		this.map.put(R.drawable.deceased, BitmapFactory.decodeResource(res, R.drawable.deceased));
		this.map.put(R.drawable.innocent, BitmapFactory.decodeResource(res, R.drawable.innocent));
		
		this.map.put(R.drawable.shift_up, BitmapFactory.decodeResource(res, R.drawable.shift_up));
		this.map.put(R.drawable.shift_down, BitmapFactory.decodeResource(res, R.drawable.shift_down));
		this.map.put(R.drawable.shift_left, BitmapFactory.decodeResource(res, R.drawable.shift_left));
		this.map.put(R.drawable.shift_right, BitmapFactory.decodeResource(res, R.drawable.shift_right));
		
		this.map.put(R.drawable.player, BitmapFactory.decodeResource(res, R.drawable.player));
		this.map.put(R.drawable.evidence, BitmapFactory.decodeResource(res, R.drawable.evidence));
	}

	public Bitmap get(int id) {
		return map.get(id);
	}
}