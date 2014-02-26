package utils.teamcity.wallt.view.wall;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import utils.teamcity.wallt.view.UIUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Math.abs;
import static javafx.beans.binding.Bindings.*;
import static javafx.geometry.Pos.CENTER_LEFT;
import static javafx.scene.text.TextAlignment.CENTER;
import static javafx.scene.text.TextAlignment.LEFT;

/**
 * Date: 23/02/14
 *
 * @author Cedric Longo
 */
final class TileView extends StackPane {

    private final TileViewModel _model;
    private final FadeTransition _runningAnimation;

    TileView( final TileViewModel build ) {
        _model = build;

        setAlignment( CENTER_LEFT );
        setStyle( "-fx-border-color:white; -fx-border-radius:5;" );
        backgroundProperty( ).bind( build.backgroundProperty( ) );

        _runningAnimation = prepareRunningAnimation( );

        final Pane progressPane = createProgressBackground( );
        final HBox tileContent = createBuildInformation( );
        getChildren( ).addAll( progressPane, tileContent );

        _model.runningProperty( ).addListener( ( o, oldVallue, newValue ) -> {
            if ( newValue ) {
                startRunningAnimation( );
            } else {
                stopRunningAnimation( );
                setOpacity( 1 );
            }
        } );
    }

    private FadeTransition prepareRunningAnimation( ) {
        final FadeTransition transition = new FadeTransition( Duration.millis( 1500 ), this );
        transition.setFromValue( 1.0 );
        transition.setToValue( 0.5 );
        transition.setCycleCount( Timeline.INDEFINITE );
        transition.setAutoReverse( true );
        return transition;
    }

    private Pane createProgressBackground( ) {
        final Pane progressPane = new Pane( );
        progressPane.backgroundProperty( ).bind( _model.runningBackgroundProperty( ) );
        progressPane.minWidthProperty( ).bind( widthProperty( ).multiply( _model.percentageCompleteProperty( ) ).divide( 100 ) );
        progressPane.maxWidthProperty( ).bind( progressPane.minWidthProperty( ) );
        progressPane.visibleProperty( ).bind( _model.runningProperty( ) );
        return progressPane;
    }

    private HBox createBuildInformation( ) {
        final HBox tileContent = new HBox( );
        tileContent.setAlignment( CENTER_LEFT );
        tileContent.setSpacing( 10 );

        final Label tileTitle = new Label( );
        tileTitle.setFont( UIUtils.font( 50, FontWeight.BOLD ) );
        tileTitle.setTextFill( Color.WHITE );
        tileTitle.setPadding( new Insets( 5 ) );
        tileTitle.setWrapText( true );
        tileTitle.setEffect( UIUtils.shadowEffect( ) );
        tileTitle.textProperty( ).bind( _model.displayedNameProperty( ) );
        tileTitle.prefWidthProperty( ).bind( widthProperty( ) );
        tileTitle.prefHeightProperty( ).bind( heightProperty( ) );
        tileTitle.alignmentProperty( ).bind( createObjectBinding( ( ) -> _model.isLightMode( ) ? Pos.CENTER : CENTER_LEFT, _model.lightModeProperty( ) ) );
        tileTitle.textAlignmentProperty( ).bind( createObjectBinding( ( ) -> _model.isLightMode( ) ? CENTER : LEFT, _model.lightModeProperty( ) ) );
        HBox.setHgrow( tileTitle, Priority.SOMETIMES );
        tileContent.getChildren( ).add( tileTitle );

        final VBox contextPart = createContextPart( _model );
        contextPart.visibleProperty( ).bind( _model.lightModeProperty( ).not( ) );
        contextPart.minWidthProperty( ).bind( createIntegerBinding( ( ) -> contextPart.isVisible( ) ? 150 : 0, contextPart.visibleProperty( ) ) );
        contextPart.maxWidthProperty( ).bind( contextPart.minWidthProperty( ) );
        tileContent.getChildren( ).add( contextPart );
        return tileContent;
    }


    private VBox createContextPart( final TileViewModel build ) {
        final VBox contextPart = new VBox( );
        contextPart.setAlignment( Pos.CENTER );

        final HBox statusBox = new HBox( );
        statusBox.setAlignment( Pos.CENTER );
        statusBox.setSpacing( 5 );

        final ImageView queuedIcon = queueImageView( build );

        final ImageView image = new ImageView( );
        image.setPreserveRatio( true );
        image.setFitWidth( 90 );
        image.imageProperty( ).bind( build.imageProperty( ) );
        statusBox.getChildren( ).addAll( queuedIcon, image );

        final HBox lastBuildInfoPart = createLastBuildInfoBox( build );
        lastBuildInfoPart.visibleProperty( ).bind( build.runningProperty( ).not( ) );

        final HBox timeLeftInfoBox = createTimeLeftInfoBox( build );
        timeLeftInfoBox.visibleProperty( ).bind( build.runningProperty( ) );

        final StackPane infoBox = new StackPane( lastBuildInfoPart, timeLeftInfoBox );
        infoBox.setAlignment( Pos.CENTER );
        infoBox.visibleProperty( ).bind( contextPart.heightProperty( ).greaterThan( 150 ) );

        contextPart.getChildren( ).addAll( statusBox, infoBox );
        return contextPart;
    }


    private ImageView queueImageView( final TileViewModel build ) {
        final ImageView queuedIcon = new ImageView( UIUtils.createImage( "queued.png" ) );
        queuedIcon.setPreserveRatio( true );
        queuedIcon.setFitWidth( 50 );
        queuedIcon.visibleProperty( ).bind( build.queuedProperty( ) );

        final RotateTransition transition = new RotateTransition( Duration.seconds( 3 ), queuedIcon );
        transition.setByAngle( 360 );
        transition.setCycleCount( Timeline.INDEFINITE );
        transition.play( );

        return queuedIcon;
    }

    private HBox createLastBuildInfoBox( final TileViewModel build ) {
        final HBox lastBuildInfoPart = new HBox( );
        lastBuildInfoPart.setSpacing( 5 );
        lastBuildInfoPart.setAlignment( Pos.CENTER );

        final ImageView lastBuildIcon = new ImageView( UIUtils.createImage( "lastBuild.png" ) );
        lastBuildIcon.setPreserveRatio( true );
        lastBuildIcon.setFitWidth( 32 );

        final Label lastBuildDate = new Label( );
        lastBuildDate.setMinWidth( 110 );
        lastBuildDate.setTextAlignment( CENTER );
        lastBuildDate.setAlignment( Pos.CENTER );
        lastBuildDate.setFont( UIUtils.font( 32, FontWeight.BOLD ) );
        lastBuildDate.setTextFill( Color.WHITE );
        lastBuildDate.setWrapText( true );
        lastBuildDate.setEffect( UIUtils.shadowEffect( ) );
        lastBuildDate.textProperty( ).bind( createStringBinding( ( ) -> {
            final LocalDateTime localDateTime = build.lastFinishedDateProperty( ).get( );
            if ( localDateTime == null )
                return "00/00\n00:00";
            return localDateTime.format( DateTimeFormatter.ofPattern( "dd/MM\nHH:mm" ) );
        }, build.lastFinishedDateProperty( ) ) );

        lastBuildInfoPart.getChildren( ).addAll( lastBuildIcon, lastBuildDate );
        return lastBuildInfoPart;
    }

    private HBox createTimeLeftInfoBox( final TileViewModel build ) {
        final HBox lastBuildInfoPart = new HBox( );
        lastBuildInfoPart.setSpacing( 5 );
        lastBuildInfoPart.setAlignment( Pos.CENTER );
        final ImageView lastBuildIcon = new ImageView( UIUtils.createImage( "timeLeft.png" ) );
        lastBuildIcon.setPreserveRatio( true );
        lastBuildIcon.setFitWidth( 32 );

        final Label timeLeftLabel = new Label( );
        timeLeftLabel.setMinWidth( 110 );
        timeLeftLabel.setTextAlignment( CENTER );
        timeLeftLabel.setAlignment( Pos.CENTER );
        timeLeftLabel.setFont( UIUtils.font( 32, FontWeight.BOLD ) );
        timeLeftLabel.setTextFill( Color.WHITE );
        timeLeftLabel.setWrapText( true );
        timeLeftLabel.setEffect( UIUtils.shadowEffect( ) );
        timeLeftLabel.textProperty( ).bind( createStringBinding( ( ) -> {
            final java.time.Duration timeLeft = build.timeLeftProperty( ).get( );
            return ( timeLeft.isNegative( ) ? "+ " : "" ) + ( abs( timeLeft.toMinutes( ) ) + 1 ) + "\nmin";
        }, build.timeLeftProperty( ) ) );

        lastBuildInfoPart.getChildren( ).addAll( lastBuildIcon, timeLeftLabel );
        return lastBuildInfoPart;
    }


    public void startRunningAnimation( ) {
        _runningAnimation.play( );
    }

    public void stopRunningAnimation( ) {
        _runningAnimation.stop( );
    }
}