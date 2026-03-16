import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LandingPage = () => {
  const navigate = useNavigate();
  const [hoveredSide, setHoveredSide] = useState(null);

  const styles = {
    container: {
      display: 'flex',
      minHeight: '100vh',
      fontFamily: "'Segoe UI', sans-serif",
      background: '#f0f7f9',
    },
    side: (thisSide) => ({
      flex: 1,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      cursor: 'pointer',
      transition: 'all 0.5s ease',
      opacity: hoveredSide && hoveredSide !== thisSide ? 0.35 : 1,
      borderRight: thisSide === 'patient' ? '1px solid rgba(79,169,195,0.2)' : 'none',
      background: thisSide === 'patient'
        ? hoveredSide === 'patient'
          ? 'linear-gradient(160deg, #e0f4f8 0%, #f0f7f9 100%)'
          : 'linear-gradient(160deg, #f0f7f9 0%, #f8fbfc 100%)'
        : hoveredSide === 'doctor'
          ? 'linear-gradient(160deg, #e8f4ee 0%, #f0f7f9 100%)'
          : 'linear-gradient(160deg, #f0f7f9 0%, #f8fbfc 100%)',
    }),
    iconWrapper: (thisSide) => ({
      position: 'relative',
      width: 140,
      height: 140,
      borderRadius: '50%',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      background: thisSide === 'patient'
        ? hoveredSide === thisSide ? 'rgba(79,169,195,0.15)' : 'rgba(79,169,195,0.08)'
        : hoveredSide === thisSide ? 'rgba(72,187,120,0.15)' : 'rgba(72,187,120,0.08)',
      transform: hoveredSide === thisSide ? 'scale(1.08)' : 'scale(1)',
      transition: 'all 0.5s ease',
      boxShadow: hoveredSide === thisSide
        ? thisSide === 'patient'
          ? '0 8px 40px rgba(79,169,195,0.25)'
          : '0 8px 40px rgba(72,187,120,0.25)'
        : '0 4px 20px rgba(0,0,0,0.06)',
    }),
    ring: (thisSide) => ({
      position: 'absolute',
      inset: -4,
      borderRadius: '50%',
      border: `2px solid ${thisSide === 'patient' ? 'rgba(79,169,195,0.4)' : 'rgba(72,187,120,0.4)'}`,
      animation: hoveredSide === thisSide ? 'ping 2.5s cubic-bezier(0,0,0.2,1) infinite' : 'none',
      opacity: hoveredSide === thisSide ? 1 : 0,
      transition: 'opacity 0.3s ease',
    }),
    emoji: (thisSide) => ({
      fontSize: 64,
      transform: hoveredSide === thisSide ? 'scale(1.1) translateY(-4px)' : 'scale(1)',
      transition: 'transform 0.5s ease',
      userSelect: 'none',
    }),
    textWrapper: {
      textAlign: 'center',
      marginTop: 24,
    },
    title: (thisSide) => ({
      fontSize: 32,
      fontWeight: '700',
      color: thisSide === 'patient' ? '#2a7d9c' : '#2a8c5a',
      margin: '0 0 8px 0',
      letterSpacing: '-0.5px',
    }),
    subtitle: {
      fontSize: 14,
      color: '#7a9aaa',
      margin: 0,
    },
    badge: (thisSide) => ({
      display: 'inline-block',
      marginTop: 12,
      padding: '5px 16px',
      borderRadius: '20px',
      fontSize: 11,
      fontWeight: '600',
      letterSpacing: '1px',
      textTransform: 'uppercase',
      background: thisSide === 'patient' ? 'rgba(79,169,195,0.12)' : 'rgba(72,187,120,0.12)',
      color: thisSide === 'patient' ? '#2a7d9c' : '#2a8c5a',
      border: `1px solid ${thisSide === 'patient' ? 'rgba(79,169,195,0.3)' : 'rgba(72,187,120,0.3)'}`,
    }),
    appTitle: {
      position: 'absolute',
      top: 28,
      left: '50%',
      transform: 'translateX(-50%)',
      fontSize: 18,
      fontWeight: '700',
      color: '#4a7a8a',
      letterSpacing: '1px',
      textTransform: 'uppercase',
      whiteSpace: 'nowrap',
    },
    divider: {
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: 40,
      height: 40,
      borderRadius: '50%',
      background: '#fff',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      boxShadow: '0 2px 12px rgba(0,0,0,0.1)',
      fontSize: 16,
      color: '#aac5ce',
      zIndex: 10,
    },
  };

  return (
    <>
      <style>{`
        @keyframes ping {
          0% { transform: scale(1); opacity: 0.7; }
          75%, 100% { transform: scale(1.5); opacity: 0; }
        }
        * { box-sizing: border-box; margin: 0; padding: 0; }
      `}</style>

      <div style={{ position: 'relative' }}>
        <div style={styles.appTitle}>🧠 Cerebro Connect</div>
        <div style={styles.divider}>+</div>

        <div style={styles.container}>
          {/* Patient Side */}
          <div
            style={styles.side('patient')}
            onMouseEnter={() => setHoveredSide('patient')}
            onMouseLeave={() => setHoveredSide(null)}
            onClick={() => navigate('/login/patient')}
          >
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
              <div style={styles.iconWrapper('patient')}>
                <span style={styles.emoji('patient')}>🧑‍🦽</span>
                <div style={styles.ring('patient')} />
              </div>
              <div style={styles.textWrapper}>
                <h1 style={styles.title('patient')}>Patient</h1>
                <p style={styles.subtitle}>Access your health portal</p>
                <span style={styles.badge('patient')}>Health Records</span>
              </div>
            </div>
          </div>

          {/* Doctor Side */}
          <div
            style={styles.side('doctor')}
            onMouseEnter={() => setHoveredSide('doctor')}
            onMouseLeave={() => setHoveredSide(null)}
            onClick={() => navigate('/login/doctor')}
          >
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
              <div style={styles.iconWrapper('doctor')}>
                <span style={styles.emoji('doctor')}>🩺</span>
                <div style={styles.ring('doctor')} />
              </div>
              <div style={styles.textWrapper}>
                <h1 style={styles.title('doctor')}>Doctor</h1>
                <p style={styles.subtitle}>Manage your practice</p>
                <span style={styles.badge('doctor')}>Clinical Dashboard</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default LandingPage;