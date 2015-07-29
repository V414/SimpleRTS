/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

/**
 *
 * @author Jack
 */
public class BlendMode {
    
    public enum Factor
    {
        Zero,             ///< (0, 0, 0, 0)
        One,              ///< (1, 1, 1, 1)
        SrcColor,         ///< (src.r, src.g, src.b, src.a)
        OneMinusSrcColor, ///< (1, 1, 1, 1) - (src.r, src.g, src.b, src.a)
        DstColor,         ///< (dst.r, dst.g, dst.b, dst.a)
        OneMinusDstColor, ///< (1, 1, 1, 1) - (dst.r, dst.g, dst.b, dst.a)
        SrcAlpha,         ///< (src.a, src.a, src.a, src.a)
        OneMinusSrcAlpha, ///< (1, 1, 1, 1) - (src.a, src.a, src.a, src.a)
        DstAlpha,         ///< (dst.a, dst.a, dst.a, dst.a)
        OneMinusDstAlpha  ///< (1, 1, 1, 1) - (dst.a, dst.a, dst.a, dst.a)
    };
    
    public enum Equation
    {
        Add,     ///< Pixel = Src * SrcFactor + Dst * DstFactor
        Subtract ///< Pixel = Src * SrcFactor - Dst * DstFactor
    };
    
    ////////////////////////////////////////////////////////////
    // Commonly used blending modes
    ////////////////////////////////////////////////////////////
    public static final BlendMode BlendAlpha = new BlendMode(   BlendMode.Factor.SrcAlpha,
                                                                BlendMode.Factor.OneMinusSrcAlpha,
                                                                BlendMode.Equation.Add,
                                                                BlendMode.Factor.One,
                                                                BlendMode.Factor.OneMinusSrcAlpha,
                                                                BlendMode.Equation.Add);
    
    public static final BlendMode BlendAdd = new BlendMode(   BlendMode.Factor.SrcAlpha,
                                                                BlendMode.Factor.One,
                                                                BlendMode.Equation.Add,
                                                                BlendMode.Factor.One,
                                                                BlendMode.Factor.One,
                                                                BlendMode.Equation.Add);
    
    public static final BlendMode BlendMultiply = new BlendMode(    BlendMode.Factor.DstColor,
                                                                    BlendMode.Factor.Zero,
                                                                    BlendMode.Equation.Add);
    
    public static final BlendMode BlendNone = new BlendMode(    BlendMode.Factor.One,
                                                                BlendMode.Factor.Zero,
                                                                BlendMode.Equation.Add );

    protected Factor   colorSrcFactor; ///< Source blending factor for the color channels
    protected Factor   colorDstFactor; ///< Destination blending factor for the color channels
    protected Equation colorEquation;  ///< Blending equation for the color channels
    protected Factor   alphaSrcFactor; ///< Source blending factor for the alpha channel
    protected Factor   alphaDstFactor; ///< Destination blending factor for the alpha channel
    protected Equation alphaEquation;  ///< Blending equation for the alpha channel

    ////////////////////////////////////////////////////////////
    public BlendMode() {
        colorSrcFactor = BlendMode.Factor.SrcAlpha;
        colorDstFactor = BlendMode.Factor.OneMinusSrcAlpha;
        colorEquation = BlendMode.Equation.Add;
        alphaSrcFactor = BlendMode.Factor.One;
        alphaDstFactor = BlendMode.Factor.OneMinusSrcAlpha;
        alphaEquation = BlendMode.Equation.Add;
    }
    
    public BlendMode(Factor sourceFactor, Factor destinationFactor, Equation blendEquation) {
        colorSrcFactor = sourceFactor;
        colorDstFactor = destinationFactor;
        colorEquation = blendEquation;
        alphaSrcFactor = sourceFactor;
        alphaDstFactor = destinationFactor;
        alphaEquation = blendEquation;
    }
    
    public BlendMode(   Factor colorSrcFactor, Factor colorDstFactor, Equation colorEquation,
                        Factor alphaSrcFactor, Factor alphaDstFactor, Equation alphaEquation) {
        this.colorSrcFactor = colorSrcFactor;
        this.colorDstFactor = colorDstFactor;
        this.colorEquation = colorEquation;
        this.alphaSrcFactor = alphaSrcFactor;
        this.alphaDstFactor = alphaDstFactor;
        this.alphaEquation = alphaEquation;
    }
    
    public static boolean equal(BlendMode left, BlendMode right) {
        return (left.colorSrcFactor == right.colorSrcFactor &&
                left.colorDstFactor == right.colorDstFactor &&
                left.colorEquation == right.colorEquation &&
                left.alphaSrcFactor == right.alphaSrcFactor &&
                left.alphaDstFactor == right.alphaDstFactor &&
                left.alphaEquation == right.alphaEquation
                        );
    }
    
    public static boolean notEqual(BlendMode left, BlendMode right) {
        return !equal(left,right);
    }
    
}
